package com.kittydaddy.metadata.vcontent.dao;

import org.apache.ibatis.annotations.Param;

import com.kittydaddy.metadata.vcontent.domain.KVContentEntity;

public interface KVContentEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(KVContentEntity record);

    int insertSelective(KVContentEntity record);

    KVContentEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(KVContentEntity record);

    int updateByPrimaryKey(KVContentEntity record);

	KVContentEntity queryKvContentBySubOriginId(@Param(value="subOriginId")String subOriginId);
}