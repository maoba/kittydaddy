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
import com.kittydaddy.metadata.vcontent.domain.KVContentEntity;
import com.kittydaddy.security.annotation.CurrentUser;
import com.kittydaddy.security.annotation.CurrentUserInfo;
import com.kittydaddy.service.vcontent.KVContentService;

@RestController
@RequestMapping(value="/kvcontent")
public class KVContentController extends BaseController{
	@Autowired
	private KVContentService kVContentService;
	
	
	@RequestMapping(method=RequestMethod.GET,value="kvcontentList.html")
    public ModelAndView queryRolesByPage(){
 	    ModelAndView view = new ModelAndView();
        view.setViewName("/page/content/kvcontentList");
        return view;
    }
	
	/**
	 * 跳转到编辑页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="editkvContent")
	public ModelAndView editkvContentInfo(@RequestParam(value="kvcontentId") String kvcontentId){
	    ModelAndView view = new ModelAndView();
	    KVContentEntity kvcontentEntity = kVContentService.querykvContentById(kvcontentId);
	    view.addObject("kvcontent",kvcontentEntity);
	    view.setViewName("/page/content/editKvcontent");
	    return view;
	}	
	
	/**
	 * 赋值ExposideExist
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="changeExposideExist")
	public String changeExposideExist(){
		kVContentService.changeExposideExist();
		return RESULT_SUCCESS;
	}
	
	
	/**
	 * 查询用户列表
	 * @param name 姓名
	 * @param status 状态
	 * @param pageIndex
	 * @param pageSize
	 * @param currentUser
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="kvcontentList")
    public BaseResponse querykvContentList(Integer shortFlag,String title,String id,Integer status,Integer pageIndex,Integer pageSize){
       PageInfo<KVContentEntity> kvcontentList = kVContentService.queryKvContentByPage(shortFlag,id,title,status,pageIndex,pageSize);
 	   return BaseResponse.getSuccessResp("查询成功",kvcontentList.getTotal(),kvcontentList.getList());
    }
	
	
	
	/**
	 * 新增或者更新内容
	 * @param params
	 * @param currentUserInfo
	 * @return
	 */
    @RequestMapping(method=RequestMethod.POST,value="saveUpdateKVContent")
    public String saveUpdateKVContent(@RequestParam Map<String,Object> params){
    	kVContentService.saveUpdateKVContent(params);
 	   return RESULT_SUCCESS;
    }
	
	
	
}
