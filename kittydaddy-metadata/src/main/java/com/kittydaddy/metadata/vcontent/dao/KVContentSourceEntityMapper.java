package com.kittydaddy.metadata.vcontent.dao;

import com.kittydaddy.metadata.vcontent.domain.KVContentSourceEntity;

public interface KVContentSourceEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(KVContentSourceEntity record);

    int insertSelective(KVContentSourceEntity record);

    KVContentSourceEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(KVContentSourceEntity record);

    int updateByPrimaryKey(KVContentSourceEntity record);
}