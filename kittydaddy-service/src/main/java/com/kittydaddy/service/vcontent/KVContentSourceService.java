package com.kittydaddy.service.vcontent;

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

}
