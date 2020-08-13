package com.biubiu.agent;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class ResourceConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/*.html").addResourceLocations("classpath:/templates/dist/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/templates/dist/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/templates/dist/css/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/templates/dist/fonts/");
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/templates/dist/img/");
        super.addResourceHandlers(registry);
    }
}
