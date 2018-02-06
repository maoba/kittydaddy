package com.kittydaddy.facade.dto.search;

import java.util.List;

//搜索剧集
public class WechatSearchItemDto {
	private String itemTitle;
	  
	private List<String> itemPlayUrls;

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public List<String> getItemPlayUrls() {
		return itemPlayUrls;
	}

	public void setItemPlayUrls(List<String> itemPlayUrls) {
		this.itemPlayUrls = itemPlayUrls;
	}
}
