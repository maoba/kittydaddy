//package com.kittydaddy.app.api;
//import java.io.IOException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import com.github.pagehelper.PageInfo;
//import com.kittydaddy.facade.dto.tenant.TenantTeacherDto;
//import com.kittydaddy.facade.dto.tenant.responsedto.PageResponse;
//import com.kittydaddy.facade.dto.tenant.responsedto.app.TeacherDetailResponse;
//import com.kittydaddy.security.annotation.CurrentUser;
//import com.kittydaddy.security.annotation.CurrentUserInfo;
//import com.kittydaddy.service.tenant.TenantTeacherService;
//@RestController
//@RequestMapping(value="/tenant/tenantTeacher")
//public class TenantTeacherApi{
//	@Autowired
//	private TenantTeacherService tenantTeacherService; 
//	/**
//	 * 分页查询
//	 * @param name  教师姓名
//	 * @param nickName 教师昵称
//	 * @param pageIndex
//	 * @param pageSize
//	 * @param currentUser
//	 * @return
//	 * @throws IOException 
//	 */
//	@SuppressWarnings("static-access")
//	@RequestMapping(method=RequestMethod.GET,value="/queryTeachersByPage",produces="application/json")
//    @ResponseBody
//    public PageResponse queryTeachersByPage(
//		@RequestParam(value="name", required=false) String name,
//		@RequestParam(value="nickName",required=false) String nickName,
//        @RequestParam(value="pageIndex", required=false,defaultValue="0") Integer pageIndex,
//        @RequestParam(value="pageSize", required=false,defaultValue="0") Integer pageSize,@CurrentUser CurrentUserInfo currentUser,HttpServletRequest request,HttpServletResponse response) {
//    	 PageResponse pageResponse = new PageResponse();
//    	 //根据租户的id以及相关的名称条件组合查询教师信息
//    	 PageInfo<TenantTeacherDto> tenantTeacherDtos = tenantTeacherService.queryTeachersByPage(name,nickName,currentUser.getTenantId(),pageIndex,pageSize);
//	     return pageResponse.getSuccessPage(tenantTeacherDtos);
//    }
//	
//	/**
//	 * 根据教师的Id查询教师的详情信息
//	 * @param id
//	 * @return
//	 */
//	@RequestMapping(method=RequestMethod.GET,value="/queryTeacherDetail",produces="application/json")
//    @ResponseBody
//    public TeacherDetailResponse queryTeacherDetail(
//		@RequestParam(value="id", required=false,defaultValue="0") Long id) {
//    	 //根据租户的id以及相关的名称条件组合查询教师信息
//		TeacherDetailResponse response = tenantTeacherService.queryTeacherById(id);
//	     return response;
//    }
//}
