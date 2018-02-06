package com.kittydaddy.metadata.pvcontent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kittydaddy.metadata.pvcontent.domain.PContentItemEntity;

public interface PContentItemEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(PContentItemEntity record);

    int insertSelective(PContentItemEntity record);

    PContentItemEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PContentItemEntity record);

    int updateByPrimaryKey(PContentItemEntity record);

	List<PContentItemEntity> findItemByContentId(@Param(value="contentId") String contentId);
}