package com.master.udd.service;

import com.master.udd.exception.EntityNotFoundException;
import com.master.udd.model.EducationLevel;
import com.master.udd.repository.EducationLevelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EducationLevelService {

    private final EducationLevelRepository educationLevelRepository;

    private final String entityName = "Education level";

    public EducationLevel findById(Long id) throws EntityNotFoundException {
        return this.educationLevelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityName, id));
    }

}
