package com.kittydaddy.search.repository.vcontent;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.kittydaddy.search.model.vcontent.SKVContentEntity;

public interface SKVContentRepository extends ElasticsearchRepository<SKVContentEntity, String>{

	SKVContentEntity findBySubOriginId(String subOriginId);

}
