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
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/applicant")
@AllArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;

    private final static Logger LOGGER = Logger.getLogger("ApplicantController.class");

    @PostMapping
    public ResponseEntity<Long> storeApplicant(@Valid @ModelAttribute ApplicantDto applicantDto)
            throws IOException, EntityNotFoundException, InvalidAddressException {
        Applicant applicant = applicantService.save(applicantDto);
        return new ResponseEntity<>(applicant.getId(), HttpStatus.OK);
    }

    @GetMapping("/cv/{cv-id}")
    public ResponseEntity<byte[]> getApplicantCv(@PathVariable("cv-id") Long cvId) throws EntityNotFoundException, IOException {
        byte[] cvFileContent = applicantService.getApplicantCv(cvId);
        return new ResponseEntity<>(cvFileContent, HttpStatus.OK);
    }

    @PostMapping("/access")
    public ResponseEntity<Void> logFormAccess(@RequestParam String ipAddress) {
        LOGGER.info("ip=" + ipAddress);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
