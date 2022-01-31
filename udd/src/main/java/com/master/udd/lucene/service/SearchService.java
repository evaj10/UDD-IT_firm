package com.master.udd.lucene.service;

import com.master.udd.dto.SearchRequest;
import com.master.udd.dto.SearchRequestField;
import com.master.udd.dto.SearchResponse;
import com.master.udd.lucene.model.CVIndex;
import lombok.AllArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.domain.Pageable;
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

    public List<SearchResponse> search(SearchRequest searchRequest, Pageable pageable) {
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
        //default fragment size of 100 characters
        //default number of fragments of 5
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                                                .highlighterType("plain")
                                                .field("cvContent");
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withHighlightBuilder(highlightBuilder)
                .withPageable(pageable)
                .build();

        SearchHits<CVIndex> searchHits =
                elasticsearchRestTemplate.search(searchQuery, CVIndex.class, IndexCoordinates.of("cvs"));

        // TODO obrada highlight-a
        List<SearchResponse> found = new ArrayList<>();
        String highlight;
        for (SearchHit<CVIndex> hit: searchHits.getSearchHits()) {
            CVIndex cvIndex = hit.getContent();
            if (hit.getHighlightFields().isEmpty()) {
                // kreiraj staticki sazetak
                highlight = "..." + cvIndex.getCvContent().substring(0, 100) + "...";
            } else {
                // dobavi kreiran dinamicki sazetak
                highlight = "..." + hit.getHighlightFields().get("cvContent").get(0) + "...";
            }
            SearchResponse searchResponse = new SearchResponse(
                    cvIndex.getApplicantName() + " " + cvIndex.getApplicantSurname(),
                    cvIndex.getApplicantLocation().getLon(),
                    cvIndex.getApplicantLocation().getLat(),
                    highlight
                    );
            found.add(searchResponse);
        }
        return found;
    }
}
