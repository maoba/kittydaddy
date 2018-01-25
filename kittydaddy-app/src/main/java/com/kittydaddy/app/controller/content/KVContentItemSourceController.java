package com.kittydaddy.app.controller.content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.PageInfo;
import com.kittydaddy.common.constant.Constants;
import com.kittydaddy.common.enums.ShortFlagEnum;
import com.kittydaddy.facade.dto.system.response.BaseResponse;
import com.kittydaddy.metadata.vcontent.domain.KVContentSourceEntity;
import com.kittydaddy.service.vcontent.KVContentSourceService;
/**
 * @author kittydaddy
 *
 */
@RestController
@RequestMapping(value="/kvcontentItemSource")
public class KVContentItemSourceController {
	
	@Autowired
	private KVContentSourceService kvContentSourceService;
	
	/**
	  * 根据内容的id找到剧集
	  * @param pageIndex
	  * @param pageSize
	  * @return
	  */
	 @RequestMapping(method=RequestMethod.GET,value="kvcontentItemSourceList")
    public BaseResponse querykvContentItemList(String relativeId,String shortFlag,Integer pageIndex,Integer pageSize){
	  String relativeType = "";	 
      if(ShortFlagEnum.LONG.getValue() == Integer.parseInt(shortFlag)){//长视频
    	  relativeType = Constants.TABLE_K_VIDEO_ITEM;
      }else if(ShortFlagEnum.SHORT.getValue() ==Integer.parseInt(shortFlag)){//短视频
    	  relativeType = Constants.TABLE_K_VIDEO_CONTENT;
      }else{
    	  return BaseResponse.getFailureResp("查询失败", 0l);
      }
      PageInfo<KVContentSourceEntity> kvcontentItemList = kvContentSourceService.queryKvContentItemSourceByPage(relativeId,relativeType,pageIndex,pageSize);
	  return BaseResponse.getSuccessResp("查询成功",kvcontentItemList.getTotal(),kvcontentItemList.getList());
    }
}
