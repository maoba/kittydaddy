package com.kittydaddy.metadata.spider.heaven.dao;

import com.kittydaddy.metadata.spider.heaven.domain.KHeavenContentResEntity;
import org.apache.ibatis.annotations.Param;

public interface KHeavenContentResEntityMapper {

    int deleteByPrimaryKey(String id);


    int insert(KHeavenContentResEntity record);


    int insertSelective(KHeavenContentResEntity record);


    KHeavenContentResEntity selectByPrimaryKey(String id);


    int updateByPrimaryKeySelective(KHeavenContentResEntity record);


    int updateByPrimaryKey(KHeavenContentResEntity record);

    KHeavenContentResEntity findByTitle(@Param(value="title") String title);
}