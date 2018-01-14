package com.kittydaddy.service.vcontent;

import java.util.Map;

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
	
	boolean executeCollectSingleVideoService(String subOriginId);
    
	/**
	 * 采集短视频内容
	 * @param map
	 */
	void executeCollectShortVideoJobService(Map<String, Object> map);
    
}
