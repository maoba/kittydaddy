package com.kittydaddy.app.controller.content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.github.pagehelper.PageInfo;
import com.kittydaddy.app.controller.system.BaseController;
import com.kittydaddy.facade.dto.system.response.BaseResponse;
import com.kittydaddy.metadata.system.domain.PermissionEntity;
import com.kittydaddy.metadata.vcontent.domain.KVContentEntity;
import com.kittydaddy.security.annotation.CurrentUser;
import com.kittydaddy.security.annotation.CurrentUserInfo;
import com.kittydaddy.service.vcontent.KVContentService;

@RestController
@RequestMapping(value="/kvcontent")
public class KVContentController extends BaseController{
	@Autowired
	private KVContentService kVContentService;
	
	@RequestMapping(method=RequestMethod.GET,value="kvdemoList.html")
    public ModelAndView queryDemoByPage(){
 	    ModelAndView view = new ModelAndView();
        view.setViewName("/page/demo");
        return view;
    }
	
	
	@RequestMapping(method=RequestMethod.GET,value="kvcontentList.html")
    public ModelAndView queryRolesByPage(){
 	    ModelAndView view = new ModelAndView();
        view.setViewName("/page/content/kvcontentList");
        return view;
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
	@RequestMapping(method=RequestMethod.POST,value="kvcontentList")
    public PageInfo<KVContentEntity> queryKvContentList(Integer shortFlag,String title,String id,Integer status,Integer pageIndex,Integer pageSize){
  	   PageInfo<KVContentEntity> kvcontentList = kVContentService.queryKvContentByPage(shortFlag,id,title,status,pageIndex,pageSize);
  	   return kvcontentList;
    }
	
	@RequestMapping(method=RequestMethod.GET,value="queryContentDemoList")
    public BaseResponse queryPermissionDemoList(Integer shortFlag,String title,String id,Integer status,Integer pageIndex,Integer pageSize){
       PageInfo<KVContentEntity> kvcontentList = kVContentService.queryKvContentByPage(shortFlag,id,title,status,pageIndex,pageSize);
 	   return BaseResponse.getSuccessResp("查询成功",kvcontentList.getTotal(),kvcontentList.getList());
    }
}
