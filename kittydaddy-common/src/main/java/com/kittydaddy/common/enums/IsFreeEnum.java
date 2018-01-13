package com.kittydaddy.common.enums;

/**
 * 
 * @author kitty daddy
 * 是否免费标识
 */
public enum IsFreeEnum {
    YES(1,"免费"),NO(2,"收费");
	private Integer value;
	
	private String description;

	private IsFreeEnum(Integer value, String description) {
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
