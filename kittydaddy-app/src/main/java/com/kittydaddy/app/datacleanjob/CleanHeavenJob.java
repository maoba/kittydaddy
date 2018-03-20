package com.kittydaddy.app.datacleanjob;

import com.kittydaddy.service.spider.heaven.KHeavenContentResService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kittydaddy
 * 清除电影天堂无效数据
 */
@Component
public class CleanHeavenJob {
    private static final Logger logger = LoggerFactory.getLogger(CleanHeavenJob.class);

    @Autowired
    private KHeavenContentResService kHeavenContentResService;

    @Scheduled(cron = "0 05 10 ? * *")
    public void fixEpisodeExistJob(){
        logger.info("*******开始修清除电影天堂无效数据********");
        Map<String,Object> map = new HashMap<String, Object>();
        kHeavenContentResService.cleanHeanContentRes(map);
        logger.info("*****电影天堂无效数据清除完毕*****");
    }
}
