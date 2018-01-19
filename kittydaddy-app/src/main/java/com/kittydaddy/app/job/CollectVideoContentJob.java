package com.kittydaddy.app.job;

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
 */
@Component
public class CollectVideoContentJob {
	private static final Logger logger = LoggerFactory.getLogger(CollectVideoContentJob.class);
	@Autowired
	private KVContentService kvContentService;
	
    @Scheduled(cron = "0 30 15 ? * *")
    public void collectVideoJob(){
    	  logger.info("*****长视频暴力获取开始执行*************");
    	  Map<String,Object> map = new HashMap<String,Object>();
    	  kvContentService.executeCollectVideoJobService(map);
    	  logger.info("*****该轮长视频导入执行完毕************");
    }
}
