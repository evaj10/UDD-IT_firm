package com.master.udd.lucene.service;

import com.master.udd.dto.SearchRequest;
import com.master.udd.dto.SearchRequestField;
import com.master.udd.lucene.model.CVIndex;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public List<CVIndex> search(SearchRequest searchRequest) {
        // za text polja ne koristiti termQuery nego matchQuery
        // (term proverava da li je identican unos onome sto je zapisano)
        // (ako je polje tekst, pretprocesira se i bude izmenje)
        // (zato ne mogu da se nadju egzaktni unosi)
        // DA LI ONDA STAVITI DA SU IME I PREZIME TEXT POLJA?
        // (da bismo mogli da nadjemo i kada ne unesemo precizno)
        // (da li ako je term radi ć i c varijanta? -> verovatno ne)
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
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
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();

        SearchHits<CVIndex> searchHits = elasticsearchRestTemplate.search(searchQuery, CVIndex.class, IndexCoordinates.of("cvs"));

        List<CVIndex> found = new ArrayList<>();
        for (SearchHit<CVIndex> hit: searchHits.getSearchHits()) {
            CVIndex cvIndex = hit.getContent();
            found.add(cvIndex);
        }
        return found;
    }
}
