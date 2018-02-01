package com.kittydaddy.common.wechat;

/**
 * @author kitty daddy
 * 文本文件实体类
 */
public class TextMessage {
	//发送对象
    private String ToUserName;
    //来源名称
    private String FromUserName;
    //创建时间
    private String CreateTime;
    //消息类型
    private String MsgType;
    //内容
    private String Content;
    //消息Id
    private String MsgId;
    
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
}
