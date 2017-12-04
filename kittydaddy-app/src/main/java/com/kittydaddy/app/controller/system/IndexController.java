package com.kittydaddy.app.controller.system;
import com.kittydaddy.facade.dto.system.response.PermissionTreeResponse;
import com.kittydaddy.security.annotation.CurrentUser;
import com.kittydaddy.security.annotation.CurrentUserInfo;
import com.kittydaddy.service.system.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping(value="/index")
public class IndexController  extends BaseController{
    @Autowired
    private PermissionService permissionService;
    
   
}
