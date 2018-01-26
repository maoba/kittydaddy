package com.kittydaddy.common.enums;

/**
 * @author kitty daddy
 * 剧集是否存在剧集
 */
public enum EpisodeExistEnum {
    YES(1,"存在"),NO(0,"不存在");
	private Integer value;
	private String desc;
	 
	 
	private EpisodeExistEnum(Integer value, String desc) {
		this.value = value;
		this.desc = desc;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	 
	 
}
