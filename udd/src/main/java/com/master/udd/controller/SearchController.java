package com.master.udd.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/search")
@AllArgsConstructor
public class SearchController {

    private SearchService searchService;

    private final static Logger LOGGER = Logger.getLogger("SearchController.class");

    @GetMapping("/basic")
    public ResponseEntity<Page<SearchResponse>> basicSearch(
            Pageable pageable,
            String query,
            HttpServletRequest request) {
        LOGGER.info("query=[" + query + "] ip=90.116.164.204");
        List<SearchResponse> foundCvs = searchService.basicSearch(query, pageable);
        return new ResponseEntity<>(
                new PageImpl<>(foundCvs, pageable, foundCvs.size()),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<SearchResponse>> search(
            Pageable pageable,
            @Valid @RequestBody SearchRequest searchRequest) throws InvalidAddressException {
        List<SearchResponse> foundCvs = searchService.search(searchRequest, pageable);
        return new ResponseEntity<>(
                new PageImpl<>(foundCvs, pageable, foundCvs.size()),
                HttpStatus.OK);
    }

}
