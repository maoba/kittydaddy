package com.kittydaddy.service.vcontent;

import com.github.pagehelper.PageInfo;
import com.kittydaddy.metadata.vcontent.domain.KVContentItemEntity;

public interface KVContentItemService {
    
	/**
	 * 分页查询剧集列表
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageInfo<KVContentItemEntity> queryKvContentItemByPage(String contentId,Integer pageIndex, Integer pageSize);
    
}
