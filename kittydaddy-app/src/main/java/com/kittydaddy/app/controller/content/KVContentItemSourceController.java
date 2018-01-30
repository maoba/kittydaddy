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
import com.kittydaddy.common.constant.Constants;
import com.kittydaddy.common.enums.ShortFlagEnum;
import com.kittydaddy.facade.dto.system.response.BaseResponse;
import com.kittydaddy.metadata.vcontent.dao.KVContentItemEntityMapper;
import com.kittydaddy.metadata.vcontent.domain.KVContentItemEntity;
import com.kittydaddy.metadata.vcontent.domain.KVContentSourceEntity;
import com.kittydaddy.security.annotation.CurrentUser;
import com.kittydaddy.security.annotation.CurrentUserInfo;
import com.kittydaddy.service.vcontent.KVContentSourceService;
/**
 * @author kittydaddy
 *
 */
@RestController
@RequestMapping(value="/kvcontentItemSource")
public class KVContentItemSourceController extends BaseController{
	
	@Autowired
	private KVContentSourceService kvContentSourceService;
	
	@Autowired
	private KVContentItemEntityMapper kvContentItemMapper;
	
	/**
	 * 跳转到列表页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="kvcontentItemSourceListPage")
	public ModelAndView querykvContentItemSourcePage(String kvcontentItemId){
		  KVContentItemEntity itemEntity = kvContentItemMapper.selectByPrimaryKey(kvcontentItemId);
		  ModelAndView view = new ModelAndView();
		  view.addObject("itemEntity", itemEntity);
	      view.setViewName("/page/content/kvcontentSourceList");
	      return view;
	}
	
	/**
	  * 根据内容的id找到剧集
	  * @param pageIndex
	  * @param pageSize
	  * @return
	  */
	@RequestMapping(method=RequestMethod.GET,value="kvcontentItemSourceList")
    public BaseResponse querykvContentItemSourceList(String relativeId,String shortFlag,Integer pageIndex,Integer pageSize){
	  String relativeType = "";	 
      if(ShortFlagEnum.LONG.getValue() == Integer.parseInt(shortFlag)){//长视频
    	  relativeType = Constants.TABLE_K_VIDEO_ITEM;
      }else if(ShortFlagEnum.SHORT.getValue() ==Integer.parseInt(shortFlag)){//短视频
    	  relativeType = Constants.TABLE_K_VIDEO_CONTENT;
      }else{
    	  return BaseResponse.getFailureResp("查询失败", 0l);
      }
      PageInfo<KVContentSourceEntity> kvcontentItemSourceList = kvContentSourceService.queryKvContentItemSourceByPage(relativeId,relativeType,pageIndex,pageSize);
	  return BaseResponse.getSuccessResp("查询成功",kvcontentItemSourceList.getTotal(),kvcontentItemSourceList.getList());
    }
	
	/**
	 * 跳转到新增播放地址的页面
	 * @param kvcontentItemId
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="addkvcontentSource")
	public ModelAndView addkvcontentSource(String relativeId,String shortFlag){
		  ModelAndView view = new ModelAndView();
		  String relativeType = "";	
		  view.addObject("relativeId", relativeId);
		  if(ShortFlagEnum.LONG.getValue() == Integer.parseInt(shortFlag)){//长视频
	    	  relativeType = Constants.TABLE_K_VIDEO_ITEM;
	      }else if(ShortFlagEnum.SHORT.getValue() ==Integer.parseInt(shortFlag)){//短视频
	    	  relativeType = Constants.TABLE_K_VIDEO_CONTENT;
	      }
		  view.addObject("relativeType", relativeType);
		  view.addObject("shortFlag",shortFlag);
	      view.setViewName("/page/content/addkvcontentSource");
	      return view;
	}
	
	
	/**
	 * 跳转到编辑页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="editkvcontentSource")
	public ModelAndView editkvcontentSource(String id){
		 ModelAndView view = new ModelAndView();
		 KVContentSourceEntity sourceEntity = kvContentSourceService.queryById(id);
		 view.addObject("sourceEntity", sourceEntity);
		 view.setViewName("/page/content/editkvcontentSource");
		 return view;
	}
	
	
	
	/**
	 * 新增或者更新播放地址
	 * @param params
	 * @param currentUserInfo
	 * @return
	 */
    @RequestMapping(method=RequestMethod.POST,value="saveUpdateKVContentItemSource")
    public String saveUpdateKVContentItemSource(@RequestParam Map<String,Object> params,@CurrentUser CurrentUserInfo currentUserInfo){
       params.put("operateId", currentUserInfo.getUserId());	
       kvContentSourceService.saveUpdateKVContentItemSource(params);
 	   return RESULT_SUCCESS;
    }
	
    
    /**
     * 删除播放地址
     * @param userId
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="deleteContentItemSource")
    public String deleteContentItemSource(@RequestParam String itemSourceId){
 	   try{
 		   //删除播放地址
 		  kvContentSourceService.delete(itemSourceId);
 	   }catch(Exception e){
 		   return RESULT_FAILURE;
 	   }
 	   return RESULT_SUCCESS;
    }
    
}
