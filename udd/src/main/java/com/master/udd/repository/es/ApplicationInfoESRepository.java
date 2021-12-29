package com.master.udd.repository.es;

import com.master.udd.model.es.ApplicationInfoES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public interface ApplicationInfoESRepository /*extends ElasticsearchRepository<ApplicationInfoES, String>*/ {
}
