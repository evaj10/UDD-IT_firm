package com.master.udd.service;

import com.master.udd.dto.ApplicantDto;
import com.master.udd.exception.EntityNotFoundException;
import com.master.udd.model.*;
import com.master.udd.model.es.CvES;
import com.master.udd.repository.ApplicantRepository;
import com.master.udd.repository.es.CvESRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ApplicantService {

//    @Autowired
//    private CvESRepository cvESRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private EducationLevelService educationService;

    @Autowired
    private ApplicantRepository applicantRepository;
//
//    public CvES save(CvES applicant) {
//        return cvESRepository.save(applicant);
//    }
//
//    public CvES findById(String id) {
//        return cvESRepository.findById(id).orElse(null);
//    }

    public Applicant save(ApplicantDto applicantDto) throws IOException, EntityNotFoundException {
        String cvFileLocation = fileStorageService.saveFile(applicantDto.getCv());
        CV cv = new CV(cvFileLocation);
        String letterFileLocation = fileStorageService.saveFile(applicantDto.getLetter());
        Letter letter = new Letter(letterFileLocation);
        EducationLevel education = educationService.findById(applicantDto.getEducationLevelId());
        // GEOCODING SERVICE ZA DOBAVLJANJE LOKACIJE
        Location location = new Location(applicantDto.getAddress(), 39.56553881520639, 2.650095237636433);
        Applicant applicant = new Applicant(
                applicantDto.getName(), applicantDto.getSurname(), applicantDto.getEmail(),
                location, education, cv, letter);
        // cuvanje u relacionu bazu
        return applicantRepository.save(applicant);
    }

}
