package com.kittydaddy.facade.dto.search;

import java.util.List;


/**
 * 微信搜索返回可用播放地址
 * @author kittydaddy
 *
 */
public class WechatSearchDto {
	//名称
    private String title;
    
    private String contentId;
    
    //剧集    
    private List<WechatSearchItemDto> items;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<WechatSearchItemDto> getItems() {
		return items;
	}

	public void setItems(List<WechatSearchItemDto> items) {
		this.items = items;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}


	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("名称:"+title+"\n");
		
		sb.append("地址: http://www.kittydaddy.cn/wx/playVideo.html?contentId="+contentId);
//		if(KCollectionUtils.isNotEmpty(items)){
//			for(WechatSearchItemDto itemDto : items){
//				sb.append("剧集:"+itemDto.getItemTitle()+"\n");
//				if(KCollectionUtils.isNotEmpty(itemDto.getItemPlayUrls())){
//					for(String str : itemDto.getItemPlayUrls()){
//						sb.append("地址:"+str+"\n");
//					}
//				}
//			}
//		}
		return sb.toString();
	}
}
