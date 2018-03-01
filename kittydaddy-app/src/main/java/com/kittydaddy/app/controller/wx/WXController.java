package com.kittydaddy.app.controller.wx;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.kittydaddy.app.controller.system.BaseController;
import com.kittydaddy.common.utils.KCollectionUtils;
import com.kittydaddy.metadata.vcontent.domain.KVContentItemEntity;
import com.kittydaddy.search.model.pvcontent.PublishContentEntity;
import com.kittydaddy.search.service.vcontent.PublishContentService;

@RestController
@RequestMapping(value="/wx")
public class WXController extends BaseController{
	@Autowired
	private PublishContentService publishContentService;
	
	@RequestMapping(method=RequestMethod.GET,value="main")
	public ModelAndView main(){
		 ModelAndView view = new ModelAndView();
		 view.setViewName("wap_main");
		 return view;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="playVideo.html")
    public ModelAndView playWXVideo(String contentId){
		ModelAndView view = new ModelAndView();
		PublishContentEntity contentEntity = publishContentService.findById(contentId);
		if(contentEntity == null) {
			view.setViewName("/page/404");
			return view;
		}
		List<KVContentItemEntity> contentItems = contentEntity.getContentItems();
		view.addObject("content", contentEntity);
		if(KCollectionUtils.isNotEmpty(contentItems) && contentItems.size()==1){
			 //跳转到电影
			 view.setViewName("/wx/dy_show");
		}else if(KCollectionUtils.isNotEmpty(contentItems) && contentItems.size()>1){
			//跳转到电视
			 view.setViewName("/wx/ds_show");
             view.addObject("lastItemTitle",contentItems.get(contentItems.size()-1).getItemTitle());
		}else{
			 view.setViewName("/page/404");
		}
        return view;
    }
	
	
}
