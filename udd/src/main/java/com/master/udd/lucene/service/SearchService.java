package com.master.udd.lucene.service;

import com.master.udd.dto.SearchRequest;
import com.master.udd.dto.SearchRequestField;
import com.master.udd.lucene.model.CVIndex;
import lombok.AllArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
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
@AllArgsConstructor
public class SearchService {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    public List<CVIndex> search(SearchRequest searchRequest) {
        // za text polja ne koristiti termQuery nego matchQuery
        // (term proverava da li je identican unos onome sto je zapisano)
        // (ako je polje tekst, pretprocesira se i bude izmenje)
        // (zato ne mogu da se nadju egzaktni unosi)
        // DA LI ONDA STAVITI DA SU IME I PREZIME TEXT POLJA?
        // (da bismo mogli da nadjemo i kada ne unesemo precizno)
        // (da li ako je term radi ć i c varijanta? -> verovatno ne)
        // https://codecurated.com/blog/elasticsearch-text-vs-keyword/
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
        // hajlajtovanje (dinamicki sazetak) ima smisla da se radi samo za sadrzaj
        // (nema mi bas smisla da se radi sazetak za ime koja je jedna rec...)

        // problem sa unified (defaultni highlighter) u kombinaciji sa phrase
        // https://github.com/elastic/elasticsearch/issues/29561
        // koristi se zbog toga plain
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                                                .highlighterType("plain")
                                                .field("cvContent");
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withHighlightBuilder(highlightBuilder)
                .build();

        SearchHits<CVIndex> searchHits = elasticsearchRestTemplate.search(searchQuery, CVIndex.class, IndexCoordinates.of("cvs"));

        // TODO obrada highlight-a
        List<CVIndex> found = new ArrayList<>();
        for (SearchHit<CVIndex> hit: searchHits.getSearchHits()) {
            CVIndex cvIndex = hit.getContent();
            found.add(cvIndex);
        }
        return found;
    }
}
