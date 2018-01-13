package com.kittydaddy.common.enums;
/**
 * @author kitty daddy
 * 发布状态枚举值
 */
public enum PublishStatusEnum {
    YES(1,"已发布") , NO(0,"未发布");
	private Integer value;
	
	private String description;

	private PublishStatusEnum(Integer value, String description) {
		this.value = value;
		this.description = description;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
