package com.kittydaddy.app.spider.liuliang;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kittydaddy.service.vcontent.KVContentService;

/**
 * @author kitty daddy
 * 采集短视频定时任务
 */
@Component
public class CollectShortContentJob {
	private static final Logger logger = LoggerFactory.getLogger(CollectShortContentJob.class);
	
	@Autowired
	private KVContentService contentService;
	
		
	//@Scheduled(cron = "0 41 13 ? * *")
    public void collectShortJob(){
		  logger.info("*******开始获取短视频信息********");
		  Map<String,Object> map = new HashMap<String, Object>();
		  contentService.executeCollectShortVideoJobService(map);
    	  logger.info("*****短视频任务获取结束*****");
    }
	
}
