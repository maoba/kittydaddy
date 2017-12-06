package com.kittydaddy.metadata.config;
import java.util.Properties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;
/**
 * MyBatis扫描接口
 * @author kitty daddy
 * @since 
 */
@Configuration
@AutoConfigureAfter(MyBatisConfig.class)
public class MyBatisMapperScannerConfig {
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.kittydaddy.metadata.*.dao");
        Properties properties = new Properties();
        properties.setProperty("notEmpty", "false");
        properties.setProperty("ORDER", "BEFORE");
        properties.setProperty("IDENTITY", "select uuid() ");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }
}
