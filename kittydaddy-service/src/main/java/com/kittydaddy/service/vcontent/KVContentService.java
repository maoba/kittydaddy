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
    
}
