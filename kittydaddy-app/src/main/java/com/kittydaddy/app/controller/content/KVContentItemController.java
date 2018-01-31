package com.kittydaddy.app.controller.content;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.github.pagehelper.PageInfo;
import com.kittydaddy.app.controller.system.BaseController;
import com.kittydaddy.facade.dto.system.response.BaseResponse;
import com.kittydaddy.metadata.vcontent.domain.KVContentItemEntity;
import com.kittydaddy.security.annotation.CurrentUser;
import com.kittydaddy.security.annotation.CurrentUserInfo;
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
	
	 
	 /**
	  * 跳转到新增剧集页面
	  * @param contentId
	  * @return
	  */
	 @RequestMapping(method=RequestMethod.GET,value="addkvcontentItem")
	 public ModelAndView addkvcontentItem(String contentId){
		  ModelAndView view = new ModelAndView();
		  view.addObject("contentId", contentId);
	      view.setViewName("/page/content/addkvcontentItem");
	      return view;
	 }
	 
	 /**
	  * 编辑内容
	  * @param id
	  * @return
	  */
	 @RequestMapping(method=RequestMethod.GET,value="editkvcontentItem")
	 public ModelAndView editkvcontentItem(String id){
		 ModelAndView view = new ModelAndView();
		 KVContentItemEntity  kvContentItemEntity = kvContentItemService.queryKvContentItemById(id);
		 view.addObject("itemEntity", kvContentItemEntity);
		 view.setViewName("/page/content/editkvcontentItem");
	     return view;
	 }
	 
	 
	 /**
	  * 根据Id删除剧集
	  * @param id
	  * @return
	  */
	 @RequestMapping(method=RequestMethod.GET,value="deleteContentItem")
	 public String deleteContentItem(@RequestParam String id){
		 try{
	 		   //删除播放地址
			 kvContentItemService.delete(id);
	 	   }catch(Exception e){
	 		   return RESULT_FAILURE;
	 	   }
	 	   return RESULT_SUCCESS;
	 }
	 
   /**
	 * 新增或者更新播放地址
	 * @param params
	 * @param currentUserInfo
	 * @return
	 */
    @RequestMapping(method=RequestMethod.POST,value="saveUpdateKVContentItem")
    public String saveUpdateKVContentItem(@RequestParam Map<String,Object> params,@CurrentUser CurrentUserInfo currentUserInfo){
       params.put("operateId", currentUserInfo.getUserId());	
       kvContentItemService.saveUpdateKVContentItem(params);
 	   return RESULT_SUCCESS;
    }
	 
}
