package com.kittydaddy.search.service.vcontent;

import java.util.List;

import com.kittydaddy.search.model.pvcontent.PublishContentEntity;

public interface PublishContentService {

	List<PublishContentEntity> findByTitle(String title);

}
