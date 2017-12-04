package com.kittydaddy.metadata.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;

@Service
@DisconfUpdateService(confFileKeys={"redis.properties"})
public class RedisPropertyUpdateCallBack implements IDisconfUpdate{

	private final static Logger logger = LoggerFactory.getLogger(RedisPropertyUpdateCallBack.class);
	
	@Autowired
    JedisConnectionFactory jedisConn;
	
	@Override
	public void reload() throws Exception {
		logger.info("reload redis.properties------------------");
		jedisConn.destroy();
    	jedisConn.setShardInfo(null);
    	jedisConn.afterPropertiesSet();
	}

}
