package com.kittydaddy.search.service.heavencontent.impl;

import com.kittydaddy.search.model.heavencontent.HeavenContentEntity;
import com.kittydaddy.search.repository.heavencontent.HeavenContentEntityRepository;
import com.kittydaddy.search.service.heavencontent.HeavenContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeavenContentServiceImpl implements HeavenContentService{

    @Autowired
    private HeavenContentEntityRepository heavenContentEntityRepository;


    @Override
    public void publish(HeavenContentEntity contentEntity) {
        heavenContentEntityRepository.save(contentEntity);
    }

    @Override
    public void deleteById(String id) {
        heavenContentEntityRepository.delete(id);
    }
}
