package com.kittydaddy.app.controller.search;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kittydaddy.facade.dto.tenant.responsedto.BaseResponse;
import com.kittydaddy.search.service.vcontent.PublishContentService;

@RestController
@RequestMapping(value="/util")
public class SearchTestController {
	 @Autowired
	 private PublishContentService publishContentService;
	 
	 @RequestMapping(method=RequestMethod.GET,value="/searchDelete")
    public BaseResponse deleteByContentId(String contentId) {
		 publishContentService.deleteByContentId(contentId);
		 return BaseResponse.getSuccessResponse();
    }
}
