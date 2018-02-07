package com.kittydaddy.search.service.vcontent;

import java.util.List;

import com.kittydaddy.search.model.pvcontent.PublishContentEntity;

public interface PublishContentService {
    
	/**
	 * 根据影视的标题搜索需要播放的内容
	 * @param title
	 * @return
	 */
	List<PublishContentEntity> findByTitle(String title);
    
	/**
	 * 根据标题名称构建回复的信息对象
	 * @param title
	 * @return
	 */
	String buildRespMsgByTitle(String title);
    
	/**
	 * 根据id查询发布的影视的内容
	 * @param contentId
	 * @return
	 */
	PublishContentEntity findById(String contentId);

}
