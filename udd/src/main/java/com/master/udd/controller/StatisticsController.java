package com.master.udd.controller;

import com.master.udd.dto.StatsResponse;
import com.master.udd.lucene.service.StatsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/stats")
@AllArgsConstructor
public class StatisticsController {

    private StatsService statsService;

    @PreAuthorize("hasRole('ROLE_HR')")
    @GetMapping("/city")
    public ResponseEntity<List<StatsResponse>> statsByCity() throws IOException {
        List<StatsResponse> response = statsService.statsByCity();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_HR')")
    @GetMapping("/day")
    public ResponseEntity<List<StatsResponse>> statsByPartOfDay() throws IOException {
        List<StatsResponse> response = statsService.statsByDay();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
