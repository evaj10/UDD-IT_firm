package com.master.udd.lucene.repository;

import com.master.udd.lucene.model.CVIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CVIndexRepository extends ElasticsearchRepository<CVIndex, String> {
}
