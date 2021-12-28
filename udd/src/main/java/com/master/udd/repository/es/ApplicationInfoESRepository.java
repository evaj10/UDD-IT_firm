package com.master.udd.repository.es;

import com.master.udd.model.es.ApplicationInfoES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ApplicationInfoESRepository extends ElasticsearchRepository<ApplicationInfoES, String> {
}
