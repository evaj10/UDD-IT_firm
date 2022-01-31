package com.master.udd.controller;

import com.master.udd.dto.SearchRequest;
import com.master.udd.lucene.model.CVIndex;
import com.master.udd.lucene.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public ResponseEntity<List<CVIndex>> search(@Valid @RequestBody SearchRequest searchRequest) {
        List<CVIndex> foundCvs = searchService.search(searchRequest);
        return new ResponseEntity<>(foundCvs, HttpStatus.OK);
    }
}
