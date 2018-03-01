package com.kittydaddy.service.vcontent;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.kittydaddy.metadata.vcontent.domain.KVContentEntity;

/**
 * @author kitty daddy
 * 影视内容基本信息
 */
public interface KVContentService {
    
	/**
	 * 采集影视长视频的内容
	 * @param map
	 */
	void executeCollectVideoJobService(Map<String, Object> map);
	
	/**
	 * 采集短视频
	 * @param subOriginId
	 * @return
	 */
	boolean executeCollectSingleVideoService(String subOriginId);
    
	/**
	 * 采集短视频内容
	 * @param map
	 */
	void executeCollectShortVideoJobService(Map<String, Object> map);
    
	/**
	 * 根绝id以及名称进行查询
	 * @param id
	 * @param name
	 * @param status
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageInfo<KVContentEntity> queryKvContentByPage(Integer shortFlag,String id, String name, Integer status,Integer pageIndex, Integer pageSize);
    
	
	/**
	 * 根据Id查询kvcontent实体信息
	 * @param contentId
	 * @return
	 */
	KVContentEntity querykvContentById(String contentId);

	/**
	 * 更新内容
	 * @param params
	 */
	void saveUpdateKVContent(Map<String, Object> params);
	
	/**
	 * 发布内容
	 * @param contentId 内容id
	 * @param operateId 操作人id
	 * @throws Exception
	 */
	boolean publishContent(String contentId, String operateId) throws Exception;
    
	/**
	 * 修复是否存在剧集标识
	 * @param map
	 */
	void fixEpisodeExistFlag(Map<String, Object> map);
}
