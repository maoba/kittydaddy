package com.kittydaddy.app.controller.wechat;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.kittydaddy.app.controller.system.BaseController;
import com.kittydaddy.common.utils.KSignUtil;
import com.kittydaddy.common.utils.KStringUtils;
import com.kittydaddy.service.wechat.WechatService;

/**
 * 微信公众号请求处理
 * @author kitty daddy
 *
 */
@RestController
@RequestMapping("/wechat")
public class WechatController extends BaseController{
	private static final Logger LOGGER = LoggerFactory.getLogger(WechatController.class);
	
	@Autowired
	private WechatService wechatService;
	
	private final static String token = "kittydaddy";
	
	 /**
     * 微信接入
     * @param wc
     * @return
     * @throws IOException 
     */
    @RequestMapping(value="/connect",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void connectWeixin(HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.setCharacterEncoding("UTF-8");  //微信服务器POST消息时用的是UTF-8编码，在接收时也要用同样的编码，否则中文会乱码；
        response.setCharacterEncoding("UTF-8"); //在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；boolean isGet = request.getMethod().toLowerCase().equals("get"); 
      
        PrintWriter out = response.getWriter();
           try {
                String signature = request.getParameter("signature");// 微信加密签名  
                String timestamp = request.getParameter("timestamp");// 时间戳  
                String nonce = request.getParameter("nonce");// 随机数  
                String echostr = request.getParameter("echostr");//随机字符串  
                // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败 
                if(KStringUtils.isNotEmpty(echostr)&&KSignUtil.checkSignature(token, signature, timestamp, nonce)){
                	LOGGER.info("Connect the weixin server is successful.");
                    out.write(echostr);
                }else{
                	wechatService.weixinPost(request);
                } 
	        } catch (Exception e) {
	            LOGGER.error("Connect the weixin server is error.");
	        }finally{
	            out.close();
	        }
        }
 }
