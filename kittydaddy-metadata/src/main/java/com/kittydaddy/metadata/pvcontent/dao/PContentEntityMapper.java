package com.kittydaddy.metadata.pvcontent.dao;

import com.kittydaddy.metadata.pvcontent.domain.PContentEntity;

public interface PContentEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(PContentEntity record);

    int insertSelective(PContentEntity record);

    PContentEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PContentEntity record);

    int updateByPrimaryKeyWithBLOBs(PContentEntity record);

    int updateByPrimaryKey(PContentEntity record);
}