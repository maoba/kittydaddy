package com.kittydaddy.common.wechat;
/**
 * @author kitty daddy
 * 自动回复图文消息
 */
public class ArticleMessage {
	//接收方帐号（收到的OpenID）
	private String ToUserName;
	//开发者微信号
	private String FromUserName;
	//消息创建时间 （整型）
    private String CreateTime;
    //news
    private String MsgType;
    //图文消息个数，限制为8条以内
    private String ArticleCount;
    //多条图文消息信息，默认第一个item为大图,注意，如果图文数超过8，则将会无响应
    private String Articles;
    //图文消息标题
    private String Title;
    //图文消息描述
    private String Description;
    //图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
    private String PicUrl;
    //点击图文消息跳转链接
    private String Url;
    
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
	public String getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(String articleCount) {
		ArticleCount = articleCount;
	}
	public String getArticles() {
		return Articles;
	}
	public void setArticles(String articles) {
		Articles = articles;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
}
