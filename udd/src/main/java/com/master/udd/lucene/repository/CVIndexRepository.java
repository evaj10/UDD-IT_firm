package com.master.udd.lucene.repository;

import com.master.udd.lucene.model.CVIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface CVIndexRepository extends Repository<CVIndex, String> {

    @Async
    <S extends CVIndex> CompletableFuture<S> save(CVIndex entity);
}
