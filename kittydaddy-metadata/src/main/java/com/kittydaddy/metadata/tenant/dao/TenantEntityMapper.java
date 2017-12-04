package com.kittydaddy.metadata.tenant.dao;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.kittydaddy.metadata.tenant.domain.TenantEntity;

public interface TenantEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TenantEntity record);

    int insertSelective(TenantEntity record);

    TenantEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TenantEntity record);

    int updateByPrimaryKey(TenantEntity record);

	List<TenantEntity> queryTenantsByName(@Param(value="name")String name);
}