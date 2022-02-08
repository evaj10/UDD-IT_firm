package com.master.udd.controller;

import com.master.udd.dto.BasicSearchRequest;
import com.master.udd.dto.SearchRequest;
import com.master.udd.dto.SearchResponse;
import com.master.udd.exception.InvalidAddressException;
import com.master.udd.lucene.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/search")
@AllArgsConstructor
public class SearchController {

    private SearchService searchService;

    @PostMapping("/basic")
    public ResponseEntity<Page<SearchResponse>> basicSearch(
            Pageable pageable,
            @Valid @RequestBody BasicSearchRequest searchRequest) {
        Page<SearchResponse> foundCvs = searchService.basicSearch(searchRequest, pageable);
        return new ResponseEntity<>(foundCvs, HttpStatus.OK);
    }

    @PostMapping("/advanced")
    public ResponseEntity<Page<SearchResponse>> search(
            Pageable pageable,
            @Valid @RequestBody SearchRequest searchRequest) throws InvalidAddressException {
        Page<SearchResponse> foundCvs = searchService.search(searchRequest, pageable);
        return new ResponseEntity<>(foundCvs, HttpStatus.OK);
    }

}
