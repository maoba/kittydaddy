package com.kittydaddy.common.enums;

/**
 * @author kitty daddy
 */
public enum SourceEnum {
    TENCENT("腾讯"),
    SOUHU("搜狐"),
    AIQIYI("爱奇艺"),
    YOUKU("优酷"),
    KU6("酷6"),
    FIVESIX("56"),
    UC("UC"),
    LETV("乐视"),
    AIPAI("爱拍"),
    OSOST("17173"),
    YY("yy秀场"),
    WY("网易"),
    FH("凤凰"),
    FX("风行"),
    BLIBLI("哔哩哔哩"),
    MGTV("芒果TV"),
    XKX("小咖秀"),
    MP("秒拍");
	
	private String value;

	private SourceEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
