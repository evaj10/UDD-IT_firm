package com.master.udd.controller;

import com.master.udd.dto.ApplicantDto;
import com.master.udd.exception.EntityNotFoundException;
import com.master.udd.exception.InvalidAddressException;
import com.master.udd.model.Applicant;
import com.master.udd.service.ApplicantService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/applicant")
@AllArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;

    @PostMapping
    public ResponseEntity<Long> storeApplicant(@Valid @ModelAttribute ApplicantDto applicantDto)
            throws IOException, EntityNotFoundException, InvalidAddressException {
        Applicant applicant = applicantService.save(applicantDto);
        return new ResponseEntity<>(applicant.getId(), HttpStatus.OK);
    }
}
