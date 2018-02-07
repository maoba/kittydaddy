package com.kittydaddy.service.wechat.impl;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kittydaddy.common.constant.Constants;
import com.kittydaddy.common.utils.KMessageUtil;
import com.kittydaddy.common.utils.KStringUtils;
import com.kittydaddy.common.wechat.TextMessage;
import com.kittydaddy.search.service.vcontent.PublishContentService;
import com.kittydaddy.service.wechat.WechatService;

/**
 * @author kitty daddy
 */
@Service
public class WechatServiceImpl implements WechatService{
	private static final Logger LOGGER = LoggerFactory.getLogger(WechatServiceImpl.class);
	
	@Autowired
	private PublishContentService publishContentService;
	
	@Override
	public String weixinPost(HttpServletRequest request) {
		String respMessage = null;
	    try {
	        // xml请求解析
	        Map<String, String> requestMap = KMessageUtil.xmlToMap(request);
	        // 发送方帐号（open_id）
	        String fromUserName = requestMap.get("FromUserName");
	        // 公众帐号
	        String toUserName = requestMap.get("ToUserName");
	        // 消息类型
	        String msgType = requestMap.get("MsgType");
	        // 消息内容
	        String content = requestMap.get("Content");
	        LOGGER.info("FromUserName is:" + fromUserName + ", ToUserName is:" + toUserName + ", MsgType is:" + msgType);
	
	        // 文本消息
	        if (msgType.equals(Constants.REQ_MESSAGE_TYPE_TEXT)) {
	            //自动回复
	            TextMessage text = new TextMessage();
	            //根据名称进行查询
	            String msg = publishContentService.buildRespMsgByTitle(content);
	            if(KStringUtils.isNotEmpty(msg)){
	            	text.setContent(msg);
	            }else{
	            	text.setContent("对不起，目前您搜索的播放源不存在，猫爸后续及时添加，请原谅~");
	            }
	            text.setToUserName(fromUserName);
	            text.setFromUserName(toUserName);
	            text.setCreateTime(new Date().getTime() + "");
	            text.setMsgType(msgType);
	            
	            respMessage = KMessageUtil.textMessageToXml(text);
	           
	        } /*else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {// 事件推送
	            String eventType = requestMap.get("Event");// 事件类型
	            
	            if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {// 订阅
	                respContent = "欢迎关注xxx公众号！";
	                return MessageResponse.getTextMessage(fromUserName , toUserName , respContent);
	            } else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {// 自定义菜单点击事件
	                String eventKey = requestMap.get("EventKey");// 事件KEY值，与创建自定义菜单时指定的KEY值对应
	                logger.info("eventKey is:" +eventKey);
	                return xxx;
	            }
	        }
	        //开启微信声音识别测试 2015-3-30
	        else if(msgType.equals("voice"))
	        {
	            String recvMessage = requestMap.get("Recognition");
	            //respContent = "收到的语音解析结果："+recvMessage;
	            if(recvMessage!=null){
	                respContent = TulingApiProcess.getTulingResult(recvMessage);
	            }else{
	                respContent = "您说的太模糊了，能不能重新说下呢？";
	            }
	            return MessageResponse.getTextMessage(fromUserName , toUserName , respContent); 
	        }
	        //拍照功能
	        else if(msgType.equals("pic_sysphoto"))
	        {
	            
	        }
	        else
	        {
	            return MessageResponse.getTextMessage(fromUserName , toUserName , "返回为空"); 
	        }*/
	        // 事件推送
	        else if (msgType.equals(Constants.REQ_MESSAGE_TYPE_EVENT)) {
	            String eventType = requestMap.get("Event");// 事件类型
	            // 订阅
	            if (eventType.equals(Constants.EVENT_TYPE_SUBSCRIBE)) {
	                TextMessage text = new TextMessage();
	                text.setContent("欢迎关注影视猫爸，目前功能正在逐步开发中，请猫粉们耐心等待，预计3月1号全面上线~");
	                text.setToUserName(fromUserName);
	                text.setFromUserName(toUserName);
	                text.setCreateTime(Long.toString(new Date().getTime()));
	                text.setMsgType(Constants.RESP_MESSAGE_TYPE_TEXT);
	                respMessage = KMessageUtil.textMessageToXml(text);
	                
	            }else if (eventType.equals(Constants.EVENT_TYPE_UNSUBSCRIBE)) {// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
	            	
	                
	            }else if (eventType.equals(Constants.EVENT_TYPE_CLICK)) {// 自定义菜单点击事件
	                String eventKey = requestMap.get("EventKey");// 事件KEY值，与创建自定义菜单时指定的KEY值对应
	                if (eventKey.equals("customer_telephone")) {
	                    TextMessage text = new TextMessage();
	                    text.setContent("正在开发中，请耐心等待，预计3月1日上线。");
	                    text.setToUserName(fromUserName);
	                    text.setFromUserName(toUserName);
	                    text.setCreateTime(new Date().getTime() + "");
	                    text.setMsgType(Constants.RESP_MESSAGE_TYPE_TEXT);
	                    
	                    respMessage = KMessageUtil.textMessageToXml(text);
	                }
	            }
	        }
	    }catch (Exception e) {
	    	e.printStackTrace();
	    	LOGGER.error("error......");
	    }
	    return respMessage;
	}
}
