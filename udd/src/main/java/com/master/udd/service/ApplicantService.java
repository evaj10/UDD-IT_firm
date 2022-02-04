package com.master.udd.service;

import com.master.udd.dto.ApplicantDto;
import com.master.udd.exception.EntityNotFoundException;
import com.master.udd.exception.InvalidAddressException;
import com.master.udd.lucene.service.Indexer;
import com.master.udd.model.*;
import com.master.udd.repository.ApplicantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ApplicantService {

    private final FileStorageService fileStorageService;
    private final LocationService locationService;
    private final EducationLevelService educationService;
    private final CvService cvService;
    private final ApplicantRepository applicantRepository;
    private final Indexer indexer;

    public Applicant save(ApplicantDto applicantDto) throws IOException, EntityNotFoundException, InvalidAddressException {
        String cvFileLocation = fileStorageService.saveFile(applicantDto.getCv());
        CV cv = new CV(cvFileLocation);
//        String letterFileLocation = fileStorageService.saveFile(applicantDto.getLetter());
//        Letter letter = new Letter(letterFileLocation);
        EducationLevel education = educationService.findById(applicantDto.getEducationLevelId());
        Location location = locationService.getLocationFromAddress(applicantDto.getAddress());
        Applicant applicant = new Applicant(
                applicantDto.getName(), applicantDto.getSurname(), applicantDto.getEmail(),
                location, education, cv);
        // cuvanje u relacionu bazu
        applicant = applicantRepository.save(applicant);
        // cuvanje u ES ineksnu strukturu
        indexer.add(applicant);
        return applicant;
    }

    public byte[] getApplicantCv(Long cvId) throws EntityNotFoundException, IOException {
        CV cv = cvService.findById(cvId);
        return fileStorageService.readFile(cv.getFileLocation());
    }
}
