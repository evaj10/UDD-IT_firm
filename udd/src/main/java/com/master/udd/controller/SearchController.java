package com.master.udd.controller;

import com.master.udd.dto.BasicSearchRequest;
import com.master.udd.dto.SearchRequest;
import com.master.udd.dto.SearchResponse;
import com.master.udd.exception.InvalidAddressException;
import com.master.udd.lucene.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/search")
@AllArgsConstructor
public class SearchController {

    private SearchService searchService;

    @PreAuthorize("hasRole('ROLE_HR')")
    @PostMapping("/basic")
    public ResponseEntity<Page<SearchResponse>> basicSearch(
            Pageable pageable,
            @Valid @RequestBody BasicSearchRequest searchRequest) {
        Page<SearchResponse> foundCvs = searchService.basicSearch(searchRequest, pageable);
        return new ResponseEntity<>(foundCvs, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_HR')")
    @PostMapping("/advanced")
    public ResponseEntity<Page<SearchResponse>> search(
            Pageable pageable,
            @Valid @RequestBody SearchRequest searchRequest) throws InvalidAddressException {
        Page<SearchResponse> foundCvs = searchService.search(searchRequest, pageable);
        return new ResponseEntity<>(foundCvs, HttpStatus.OK);
    }

}
