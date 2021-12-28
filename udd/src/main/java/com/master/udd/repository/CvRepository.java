package com.master.udd.repository;

import com.master.udd.model.CV;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvRepository extends JpaRepository<CV, Long> {
}
