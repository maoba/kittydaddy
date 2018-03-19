package com.kittydaddy.search.service.vcontent.impl;

import java.util.ArrayList;
import java.util.List;

import com.kittydaddy.common.utils.KStringUtils;
import com.kittydaddy.facade.dto.search.DownLoadSourceDto;
import com.kittydaddy.search.model.heavencontent.HeavenContentEntity;
import com.kittydaddy.search.repository.heavencontent.HeavenContentEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kittydaddy.common.utils.KCollectionUtils;
import com.kittydaddy.facade.dto.search.WechatSearchDto;
import com.kittydaddy.facade.dto.search.WechatSearchItemDto;
import com.kittydaddy.metadata.vcontent.domain.KVContentItemEntity;
import com.kittydaddy.metadata.vcontent.domain.KVContentSourceEntity;
import com.kittydaddy.search.model.pvcontent.PublishContentEntity;
import com.kittydaddy.search.repository.pvcontent.PublishContentEntityRepository;
import com.kittydaddy.search.service.vcontent.PublishContentService;

@Service
public class PublishContentServiceImpl implements PublishContentService{
	   
    private PublishContentEntityRepository publishContentEntityRepository;

    @Autowired
    private HeavenContentEntityRepository heavenContentEntityRepository;

    @Autowired
	public void setPublishContentEntityRepository(PublishContentEntityRepository publishContentEntityRepository) {
		this.publishContentEntityRepository = publishContentEntityRepository;
	}

	@Override
	public List<PublishContentEntity> findByTitle(String title) {
		return publishContentEntityRepository.findByTitle(title);
	}
	
	@Override
	public PublishContentEntity findById(String contentId) {
		return publishContentEntityRepository.findOne(contentId);
	}

	@Override
	public String buildRespMsgByTitle(String title) {
		StringBuffer respMsg = new StringBuffer();
		List<PublishContentEntity> publishContentEntities = publishContentEntityRepository.findByTitle(title);
		if(KCollectionUtils.isNotEmpty(publishContentEntities)){
			for(PublishContentEntity pContentEntity : publishContentEntities){
				String titleStr = pContentEntity.getTitle();
				WechatSearchDto wechatSearchDto = new WechatSearchDto();
				//设置电影名称
				wechatSearchDto.setTitle(titleStr);
				wechatSearchDto.setContentId(pContentEntity.getId());
				List<WechatSearchItemDto> searchItems = new ArrayList<WechatSearchItemDto>(); 
				
				List<KVContentItemEntity> contentItems = pContentEntity.getContentItems();
				if(KCollectionUtils.isEmpty(contentItems)) return null;
				for(KVContentItemEntity contentItem : contentItems){
					
					WechatSearchItemDto searchItemDto = new WechatSearchItemDto();
					searchItemDto.setItemTitle(contentItem.getItemTitle());
					List<String> itemPlayUrls = new ArrayList<String>();
					
					List<KVContentSourceEntity> sourceEntities = contentItem.getContentSourceList();
					if(KCollectionUtils.isNotEmpty(sourceEntities)){
						for(KVContentSourceEntity source : sourceEntities){
							itemPlayUrls.add(source.getPlayUrl());
						}
					}
					if(KCollectionUtils.isEmpty(itemPlayUrls)) return null;
					searchItemDto.setItemPlayUrls(itemPlayUrls);
					searchItems.add(searchItemDto);
				}
				
				wechatSearchDto.setItems(searchItems);
				
				if(KCollectionUtils.isEmpty(searchItems)) return null;
				String resp = wechatSearchDto.toString();
				respMsg.append(resp+"\n\n");
			}
		}

		List<HeavenContentEntity> heavenContentEntities = heavenContentEntityRepository.findByTitle(title);

		if(KCollectionUtils.isNotEmpty(heavenContentEntities)){
           for(HeavenContentEntity heavenContentEntity : heavenContentEntities){
			    List<String> sourceList = new ArrayList<>();
			    if(KStringUtils.isNotEmpty(heavenContentEntity.getDownloadUrl())){
			    	String[] sources = heavenContentEntity.getDownloadUrl().split(":~~~~:");
                    if(sources != null && sources.length > 0){
                         for(String source : sources){
                              sourceList.add(source+"\n");
						 }
					}
				}
           	    respMsg.append("电影名称："+ heavenContentEntity.getTitle() +"\n\n");
           	    respMsg.append("迅雷下载：\n" + sourceList.toString().substring(1,sourceList.toString().length()-1) +"\n\n");
		   }
		}
		return respMsg.toString();
	}

	@Override
	public void deleteByContentId(String contentId) {
		PublishContentEntity pubEntity = publishContentEntityRepository.findOne(contentId);
		publishContentEntityRepository.delete(pubEntity);
	}
}
