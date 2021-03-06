package com.kittydaddy.app.spider.heaven;

import com.kittydaddy.service.spider.heaven.KHeavenContentResService;
import com.kittydaddy.service.vcontent.KVContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kitty daddy
 * 采集电影天堂数据
 */
@Component
public class CollectHeavenContentJob {

    private static final Logger logger = LoggerFactory.getLogger(CollectHeavenContentJob.class);

    @Autowired
    private KHeavenContentResService heavenContentResService;


    @Scheduled(cron = "0 40 15 ? * *")
    public void collectShortJob(){
        logger.info("*******开启当日电影天堂信息采集********");
        Map<String,Object> map = new HashMap<String, Object>();
        heavenContentResService.startCollectHeavenResource(map);
        logger.info("*****当日电影天堂信息采集结束*****");
    }
}
