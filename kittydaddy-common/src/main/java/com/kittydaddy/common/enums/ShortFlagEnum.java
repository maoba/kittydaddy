package com.kittydaddy.common.enums;

/**
 * @author kitty daddy
 * 长短视频标识
 */
public enum ShortFlagEnum {
	LONG(0,"长视频"),SHORT(1,"短视频");
    private Integer value;
    
    private String description;

	private ShortFlagEnum(Integer value, String description) {
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
