package com.kittydaddy.search.service.vcontent.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kittydaddy.search.repository.vcontent.SKVContentItemRepository;
import com.kittydaddy.search.service.vcontent.SKVContentItemService;

@Service
public class SKVContentItemServiceImpl implements SKVContentItemService{
    private SKVContentItemRepository skvContentItemRepository;

    @Autowired
	public void setSkvContentItemRepository(SKVContentItemRepository skvContentItemRepository) {
		this.skvContentItemRepository = skvContentItemRepository;
	}
    
    
}
