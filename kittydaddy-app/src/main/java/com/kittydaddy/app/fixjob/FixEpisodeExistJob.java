package com.kittydaddy.app.fixjob;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.kittydaddy.service.vcontent.KVContentService;

/**
 * 修复影视内容标识
 */
@Component
public class FixEpisodeExistJob {
	private static final Logger logger = LoggerFactory.getLogger(FixEpisodeExistJob.class);
	
	@Autowired
	private KVContentService kvContentService;
	
	@Scheduled(cron = "0 00 14 ? * *")
    public void fixEpisodeExistJob(){
		  logger.info("*******开始修复播放地址存在标识********");
		  Map<String,Object> map = new HashMap<String, Object>();
		  kvContentService.fixEpisodeExistFlag(map);
    	  logger.info("*****播放地址存在标识修复完毕*****");
    }
}
