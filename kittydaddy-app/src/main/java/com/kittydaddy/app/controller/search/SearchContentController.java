package com.kittydaddy.app.controller.search;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.kittydaddy.facade.dto.tenant.responsedto.BaseResponse;
import com.kittydaddy.search.model.pvcontent.PublishContentEntity;
import com.kittydaddy.search.service.vcontent.PublishContentService;

@RestController
@RequestMapping(value="/search")
public class SearchContentController {
	 @Autowired
	 private PublishContentService publishContentService;
	 
	 @RequestMapping(method=RequestMethod.GET,value="/searchContentByTitle")
     public BaseResponse searchContentByTitle(String title) {
		 List<PublishContentEntity> content =  publishContentService.findByTitle(title);
		 return BaseResponse.getSuccessResponse(content);
     }
}
