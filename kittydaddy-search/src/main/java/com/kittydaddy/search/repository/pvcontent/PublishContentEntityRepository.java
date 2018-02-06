package com.kittydaddy.search.repository.pvcontent;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.kittydaddy.search.model.pvcontent.PublishContentEntity;

public interface PublishContentEntityRepository extends ElasticsearchRepository<PublishContentEntity, String>{
	List<PublishContentEntity> findByTitle(String title);
}
