package com.master.udd.controller;

import com.master.udd.dto.ApplicantDto;
import com.master.udd.exception.EntityNotFoundException;
import com.master.udd.model.Applicant;
import com.master.udd.model.es.ApplicationInfoES;
import com.master.udd.model.es.CvES;
import com.master.udd.repository.es.ApplicationInfoESRepository;
import com.master.udd.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/applicant")
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private ApplicationInfoESRepository applicationInfoESRepository;

    @GetMapping
    public String getApplicantById(@PathVariable String id) {
        CvES applicant = applicantService.findById(id);
        return applicant.getApplicantName();
    }

    @PostMapping
    public ResponseEntity<Applicant> storeApplicant(@Valid @ModelAttribute ApplicantDto applicantDto)
            throws IOException, EntityNotFoundException {
        Applicant applicant = applicantService.save(applicantDto);
        CvES applicantES =
                applicantService.save(new CvES("Eva", "Janković", 6, "Sadržaj CV-a je jako kratak."));
        ApplicationInfoES access = new ApplicationInfoES();
        applicationInfoESRepository.save(access);
        return new ResponseEntity<>(applicant, HttpStatus.OK);
    }
}
