package com.kittydaddy.app;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
 * @author kitty daddy
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.kittydaddy"})
public class SystemApplication extends WebMvcConfigurerAdapter{
	
	  @Override
	  public void addResourceHandlers(ResourceHandlerRegistry registry) {
	      registry.addResourceHandler("/static/**")
	              .addResourceLocations("classpath:/static/");
	  }
	 
	 public static void main(String[] args) {
	        // 程序启动入口
	        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
	        SpringApplication.run(SystemApplication.class,args);
	    }
}
