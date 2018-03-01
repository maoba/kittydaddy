package com.kittydaddy.app.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.kittydaddy.app.resolver.HanderCurrentUserResolver;
import com.kittydaddy.app.resolver.LoginInterceptor;

/**
 * @author kitty daddy
 *  配置注解拦截
 */
@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{
	 @Autowired
	 private HanderCurrentUserResolver handerCurrentUserResolver;
	 
     
	 @Bean
	 public LoginInterceptor loginInterceptor() {
	    return new LoginInterceptor();
	 }
	  
	 @Override
     public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**/*.do")
                .addPathPatterns("/**/*.html");
        super.addInterceptors(registry);
     }
	 
     public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
         argumentResolvers.add(handerCurrentUserResolver);
     }
     
     @Override
     public void addViewControllers(ViewControllerRegistry registry) {
         registry.addViewController("/").setViewName("forward:/wx/main");
         registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
         super.addViewControllers(registry);
     } 
} 
