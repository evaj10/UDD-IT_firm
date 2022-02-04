package com.master.udd.service;

import com.master.udd.exception.EntityNotFoundException;
import com.master.udd.model.CV;
import com.master.udd.repository.CvRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CvService {

    private final CvRepository cvRepository;

    private final String entityName = "CV";

    public CV findById(Long id) throws EntityNotFoundException {
        return this.cvRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityName, id));
    }

}
