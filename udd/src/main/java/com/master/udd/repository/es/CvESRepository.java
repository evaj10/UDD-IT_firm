package com.master.udd.repository.es;

import com.master.udd.model.es.CvES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CvESRepository extends ElasticsearchRepository<CvES, String> {
}
