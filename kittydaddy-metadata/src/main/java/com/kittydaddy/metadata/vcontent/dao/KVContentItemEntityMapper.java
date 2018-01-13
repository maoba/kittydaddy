package com.kittydaddy.metadata.vcontent.dao;

import org.apache.ibatis.annotations.Param;

import com.kittydaddy.metadata.vcontent.domain.KVContentItemEntity;

public interface KVContentItemEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(KVContentItemEntity record);

    int insertSelective(KVContentItemEntity record);

    KVContentItemEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(KVContentItemEntity record);

    int updateByPrimaryKey(KVContentItemEntity record);

	KVContentItemEntity queryItemTitleAndPeriod(@Param(value="itemTitle")String itemTitle,@Param(value="itemPeriod")String itemPeriod);
}