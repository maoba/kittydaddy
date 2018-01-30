package com.kittydaddy.service.vcontent;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.kittydaddy.metadata.vcontent.domain.KVContentSourceEntity;

/**
 * @author kitty daddy
 * 影视播放地址信息
 */
public interface KVContentSourceService {
    
	/**
	 * 根据关联类型以及关联Id查询剧集信息
	 * @param relativeId
	 * @param relativeType
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageInfo<KVContentSourceEntity> queryKvContentItemSourceByPage(String relativeId, String relativeType,Integer pageIndex, Integer pageSize);
    
	/**
	 * 更新或者新增源地址
	 * @param params
	 */
	void saveUpdateKVContentItemSource(Map<String, Object> params);
    
	/**
	 * 根据id进行删除播放地址
	 * @param itemSourceId
	 */
	void delete(String itemSourceId);
    
	/**
	 * 根据id查询实体类
	 * @param id
	 * @return
	 */
	KVContentSourceEntity queryById(String id);

}
