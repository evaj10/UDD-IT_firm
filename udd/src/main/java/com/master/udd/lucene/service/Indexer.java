package com.master.udd.lucene.service;

import com.master.udd.lucene.handler.PDFHandler;
import com.master.udd.lucene.model.CVIndex;
import com.master.udd.lucene.repository.CVIndexRepository;
import com.master.udd.model.Applicant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class Indexer {

    @Autowired
    private CVIndexRepository cvRepository;

    @Autowired
    private PDFHandler fileHandler;

    @Async
    public void add(Applicant applicant) {
        String filename = applicant.getCv().getFileLocation();
        String cvContent = fileHandler.getText(new File(filename));
        CVIndex cvIndex = new CVIndex(applicant.getName(), applicant.getSurname(),
                applicant.getEducationLevel().getLevel(), cvContent);

        cvRepository.save(cvIndex);
    }

}