package com.master.udd.controller;

import com.master.udd.dto.SearchRequest;
import com.master.udd.dto.SearchResponse;
import com.master.udd.lucene.model.CVIndex;
import com.master.udd.lucene.service.SearchService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/search")
@AllArgsConstructor
public class SearchController {

    private SearchService searchService;

    @GetMapping("/basic")
    public ResponseEntity<Page<SearchResponse>> basicSearch(
            Pageable pageable,
            String query) {
        List<SearchResponse> foundCvs = searchService.basicSearch(query, pageable);
        return new ResponseEntity<>(
                new PageImpl<>(foundCvs, pageable, foundCvs.size()),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<SearchResponse>> search(
            Pageable pageable,
            @Valid @RequestBody SearchRequest searchRequest) {
        List<SearchResponse> foundCvs = searchService.search(searchRequest, pageable);
        return new ResponseEntity<>(
                new PageImpl<>(foundCvs, pageable, foundCvs.size()),
                HttpStatus.OK);
    }
}
