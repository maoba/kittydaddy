package com.kittydaddy.search.service.vcontent.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kittydaddy.search.repository.vcontent.SKVContentSourceRepository;
import com.kittydaddy.search.service.vcontent.SKVContentSourceService;

@Service
public class SKVContentSourceServiceImpl implements SKVContentSourceService{
	private SKVContentSourceRepository SKVContentSourceRepository;
	@Autowired
	public void setSKVContentSourceRepository(SKVContentSourceRepository sKVContentSourceRepository) {
		SKVContentSourceRepository = sKVContentSourceRepository;
	}   
	
}
