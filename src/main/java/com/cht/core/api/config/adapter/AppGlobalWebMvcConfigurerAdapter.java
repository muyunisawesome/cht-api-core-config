package com.cht.core.api.config.adapter;

import com.cht.core.api.config.interceptor.AppGlobalHandlerInterceptor;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import java.util.List;

public class AppGlobalWebMvcConfigurerAdapter implements WebMvcConfigurer, ChtHttpMessageConverter {
    public AppGlobalWebMvcConfigurerAdapter() {
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
        CacheControl nocache = CacheControl.noCache();
        webContentInterceptor.addCacheMapping(nocache, new String[]{"/**"});
        registry.addInterceptor(webContentInterceptor);
        registry.addInterceptor(new AppGlobalHandlerInterceptor()).excludePathPatterns(new String[]{"/swagger*", "/webjars/**", "/v2/api-docs"});
    }

    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false);
    }

    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        this.setT3HttpMessageConverter(converters);
    }
}
