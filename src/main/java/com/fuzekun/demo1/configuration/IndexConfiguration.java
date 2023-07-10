package com.fuzekun.demo1.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: Zekun Fu
 * @date: 2022/9/15 21:47
 * @Description:
 */

// 因为使用了thymeleaf模板，所以这个类没有必要使用了呀,除非想跳转到其他非默认页面
@Configuration
public class IndexConfiguration implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index");
    }
}