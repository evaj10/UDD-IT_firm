package com.master.udd.lucene.service;

import com.master.udd.dto.StatsResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatsService {

    @Autowired
    private RestHighLevelClient client;

    public List<StatsResponse> statsByCity() throws IOException {
        SearchRequest searchRequest = new SearchRequest("logstash-2022.02.07-000001");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        TermsAggregationBuilder aggregation = AggregationBuilders.terms("cities")
                .field("geoip.city_name.keyword");
        searchSourceBuilder.aggregation(aggregation);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        Aggregations aggregations = searchResponse.getAggregations();
        Terms cities = aggregations.get("cities");
        List<StatsResponse> response = new ArrayList<>();
        for (Terms.Bucket bucket : cities.getBuckets()) {
            response.add(new StatsResponse(bucket.getKeyAsString(), bucket.getDocCount()));
//            System.out.println("Bucket name: " + bucket.getKeyAsString() + ", Bucket count: " + bucket.getDocCount());
        }
        return response;
    }

    public List<StatsResponse> statsByDay() throws IOException {
        SearchRequest searchRequest = new SearchRequest("logstash-2022.02.07-000001");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        RangeAggregationBuilder aggregation = AggregationBuilders.range("timeOfDay")
                .field("hour")
                .addRange("night", 0, 6)
                .addRange("morning", 6, 12)
                .addRange("afternoon", 12, 20)
                .addRange("evening", 20, 24);
        searchSourceBuilder.aggregation(aggregation);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        Aggregations aggregations = searchResponse.getAggregations();
        Range timeOfDay = aggregations.get("timeOfDay");
        List<StatsResponse> response = new ArrayList<>();
        for (Range.Bucket bucket : timeOfDay.getBuckets()) {
            response.add(new StatsResponse(bucket.getKeyAsString(), bucket.getDocCount()));
//            System.out.println("Bucket name: " + bucket.getKeyAsString() + ", Bucket count: " + bucket.getDocCount());
        }
        return response;
    }
}
