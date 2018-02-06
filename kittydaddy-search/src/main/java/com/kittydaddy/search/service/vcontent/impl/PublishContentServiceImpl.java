package com.kittydaddy.search.service.vcontent.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kittydaddy.search.model.pvcontent.PublishContentEntity;
import com.kittydaddy.search.repository.pvcontent.PublishContentEntityRepository;
import com.kittydaddy.search.service.vcontent.PublishContentService;

@Service
public class PublishContentServiceImpl implements PublishContentService{
	   
    private PublishContentEntityRepository publishContentEntityRepository;

    @Autowired
	public void setPublishContentEntityRepository(PublishContentEntityRepository publishContentEntityRepository) {
		this.publishContentEntityRepository = publishContentEntityRepository;
	}

	@Override
	public List<PublishContentEntity> findByTitle(String title) {
		return publishContentEntityRepository.findByTitle(title);
	}
       
       
}
