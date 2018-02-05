package com.kittydaddy.search.service.vcontent.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kittydaddy.search.repository.vcontent.SKVContentRepository;
import com.kittydaddy.search.service.vcontent.SKVContentService;

@Service
public class SKVContentServiceImpl implements SKVContentService{
    private SKVContentRepository skvContentRepository;

    @Autowired
	public void setSkvContentRepository(SKVContentRepository skvContentRepository) {
		this.skvContentRepository = skvContentRepository;
	}
}
