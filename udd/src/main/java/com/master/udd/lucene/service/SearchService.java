package com.master.udd.lucene.service;

import com.master.udd.dto.*;
import com.master.udd.exception.InvalidAddressException;
import com.master.udd.lucene.model.CVIndex;
import com.master.udd.model.Location;
import com.master.udd.service.LocationService;
import lombok.AllArgsConstructor;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SearchService {

    private final LocationService locationService;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    public Page<SearchResponse> basicSearch(BasicSearchRequest searchRequest, Pageable pageable) {
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .highlighterType("plain")
                .field("cvContent")
                .preTags("<b>")
                .postTags("</b>");
        NativeSearchQuery searchQuery;
        switch (searchRequest.getField()) {
            case "applicantName":
                searchQuery = new NativeSearchQueryBuilder()
                        .withQuery(QueryBuilders.matchQuery("applicantName", searchRequest.getQuery()))
                        .withPageable(pageable)
                        .build();
                break;
            case "applicantSurname":
                searchQuery = new NativeSearchQueryBuilder()
                        .withQuery(QueryBuilders.matchQuery("applicantSurname", searchRequest.getQuery()))
                        .withPageable(pageable)
                        .build();
                break;
            case "applicantEducation":
                searchQuery = new NativeSearchQueryBuilder()
                        .withQuery(QueryBuilders.matchQuery("applicantEducation", searchRequest.getQuery()))
                        .withPageable(pageable)
                        .build();
                break;
            case "cvContent":
                searchQuery = new NativeSearchQueryBuilder()
                        .withQuery(QueryBuilders.matchQuery("cvContent", searchRequest.getQuery()))
                        .withHighlightBuilder(highlightBuilder)
                        .withPageable(pageable)
                        .build();
                break;
            default:
                searchQuery = new NativeSearchQueryBuilder()
                        .withQuery(multiMatchQuery(searchRequest.getQuery())
                                .field("applicantName")
                                .field("applicantSurname")
                                .field("cvContent")
                                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
                        .withHighlightBuilder(highlightBuilder)
                        .withPageable(pageable)
                        .build();
                break;
        }
        SearchHits<CVIndex> searchHits =
                elasticsearchRestTemplate.search(searchQuery, CVIndex.class, IndexCoordinates.of("cvs"));

        return toSearchResponse(searchHits, pageable);
    }

    public Page<SearchResponse> search(SearchRequest searchRequest, Pageable pageable) throws InvalidAddressException {
        // https://codecurated.com/blog/elasticsearch-text-vs-keyword/
        // ako je pretraga po nekom od polja
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (searchRequest.getFields() != null && !searchRequest.getFields().isEmpty()) {
            for (SearchRequestField searchField : searchRequest.getFields()) {
                if (searchField.isMustContain()) {
                    // must contain
                    if (searchField.isPhrase()) {
                        queryBuilder.must(QueryBuilders.matchPhraseQuery(searchField.getField(), searchField.getQuery()));
                    } else {
                        queryBuilder.must(QueryBuilders.matchQuery(searchField.getField(), searchField.getQuery()));
                    }
                } else {
                    // should contain
                    if (searchField.isPhrase()) {
                        queryBuilder.should(QueryBuilders.matchPhraseQuery(searchField.getField(), searchField.getQuery()));
                    } else {
                        queryBuilder.should(QueryBuilders.matchQuery(searchField.getField(), searchField.getQuery()));
                    }
                }
            }
        }
        RangeRequest rangeRequest = searchRequest.getRangeRequest();
        if (rangeRequest != null) {
            RangeQueryBuilder rangeBuilder = new RangeQueryBuilder(rangeRequest.getField())
                                                    .from(rangeRequest.getLowerBound(), true)
                                                    .to(rangeRequest.getUpperBound(), true);
            if (rangeRequest.isMustContain()) {
                queryBuilder.must(rangeBuilder);
            } else {
                queryBuilder.should(rangeBuilder);
            }
        }
        // hajlajtovanje (dinamicki sazetak) ima smisla da se radi samo za sadrzaj
        // problem sa unified (defaultni highlighter) u kombinaciji sa phrase
        // https://github.com/elastic/elasticsearch/issues/29561
        // koristi se zbog toga plain
        //default fragment size of 100 characters
        //default number of fragments of 5
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                                                .highlighterType("plain")
                                                .field("cvContent")
                                                .preTags("<b>")
                                                .postTags("</b>");

        // pretraga po geolokaciji
        GeoDistanceQueryBuilder geoDistanceBuilder = null;
        if (searchRequest.getGeoLocation() != null) {
            Location location = locationService.getLocationFromAddress(searchRequest.getGeoLocation().getCityName());
            geoDistanceBuilder = new GeoDistanceQueryBuilder("applicantLocation")
                    .point(location.getLat(), location.getLon())
                    .distance(searchRequest.getGeoLocation().getRadius(),
                            DistanceUnit.parseUnit(searchRequest.getGeoLocation().getRadiusUnit(),
                                    DistanceUnit.KILOMETERS));
        }
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withHighlightBuilder(highlightBuilder)
                .withFilter(geoDistanceBuilder)
                .withPageable(pageable)
                .build();

        SearchHits<CVIndex> searchHits =
                elasticsearchRestTemplate.search(searchQuery, CVIndex.class, IndexCoordinates.of("cvs"));

        return toSearchResponse(searchHits, pageable);
    }

    Page<SearchResponse> toSearchResponse(SearchHits<CVIndex> searchHits, Pageable pageable) {
        List<SearchResponse> found = new ArrayList<>();
        String highlight;
        for (SearchHit<CVIndex> hit: searchHits.getSearchHits()) {
            CVIndex cvIndex = hit.getContent();
            if (hit.getHighlightFields().isEmpty()) {
                // kreiraj staticki sazetak
                highlight = cvIndex.getCvContent().substring(0, 100) + "...";
            } else {
                // dobavi kreiran dinamicki sazetak
                highlight = "..." + hit.getHighlightFields().get("cvContent").get(0) + "...";
            }
            SearchResponse searchResponse = new SearchResponse(
                    cvIndex.getApplicantName() + " " + cvIndex.getApplicantSurname(),
                    cvIndex.getApplicantEmail(),
                    cvIndex.getApplicantEducationName(),
                    cvIndex.getApplicantAddress(),
                    highlight,
                    cvIndex.getCvId()
            );
            found.add(searchResponse);
        }
        return new PageImpl<>(found, pageable, searchHits.getTotalHits());
    }
}
