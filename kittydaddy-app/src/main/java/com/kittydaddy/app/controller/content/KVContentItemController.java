package com.kittydaddy.app.controller.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.PageInfo;
import com.kittydaddy.app.controller.system.BaseController;
import com.kittydaddy.facade.dto.system.response.BaseResponse;
import com.kittydaddy.metadata.vcontent.domain.KVContentItemEntity;
import com.kittydaddy.service.vcontent.KVContentItemService;

/**
 * @author kittydaddy
 */
@RestController
@RequestMapping(value="/kvcontentItem")
public class KVContentItemController extends BaseController{
     
	 @Autowired
	 private KVContentItemService kvContentItemService;
	
	 /**
	  * 根据内容的id找到剧集
	  * @param pageIndex
	  * @param pageSize
	  * @return
	  */
	 @RequestMapping(method=RequestMethod.GET,value="kvcontentItemList")
     public BaseResponse querykvContentItemList(String contentId,Integer pageIndex,Integer pageSize){
       PageInfo<KVContentItemEntity> kvcontentItemList = kvContentItemService.queryKvContentItemByPage(contentId,pageIndex,pageSize);
 	   return BaseResponse.getSuccessResp("查询成功",kvcontentItemList.getTotal(),kvcontentItemList.getList());
     }
	
}
