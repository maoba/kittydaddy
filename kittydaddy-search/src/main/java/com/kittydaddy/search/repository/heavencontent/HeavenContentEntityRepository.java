package com.kittydaddy.search.repository.heavencontent;

import com.kittydaddy.search.model.heavencontent.HeavenContentEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface HeavenContentEntityRepository extends ElasticsearchRepository<HeavenContentEntity, String> {

    List<HeavenContentEntity> findByTitle(String title);
}
