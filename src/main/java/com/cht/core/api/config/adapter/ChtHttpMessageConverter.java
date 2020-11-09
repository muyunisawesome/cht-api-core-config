package com.cht.core.api.config.adapter;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.cht.core.api.config.convert.ChtHttpMessageConverterProvider;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.smile.MappingJackson2SmileHttpMessageConverter;

import java.util.List;

public interface ChtHttpMessageConverter {

    default List<HttpMessageConverter<?>> setChtHttpMessageConverter(List<HttpMessageConverter<?>> converters) {
        converters.removeIf((converter) -> {
            return converter instanceof MappingJackson2HttpMessageConverter || converter instanceof MappingJackson2SmileHttpMessageConverter || converter instanceof MappingJackson2CborHttpMessageConverter || converter instanceof FastJsonHttpMessageConverter;
        });
        if (this.useSpringDefaultHttpMessageConverter()) {
            converters.add(supportSpringDefaultHttpMessageConverterTimestamp());
        } else if (this.fastJsonSerializationByTimestamp()) {
            converters.add(fastJsonHttpMessageConverterTimestamp());
        } else {
            converters.add(fastJsonHttpMessageConverterUTC());
        }

        return converters;
    }

    default boolean useSpringDefaultHttpMessageConverter() {
        return false;
    }

    default boolean fastJsonSerializationByTimestamp() {
        return false;
    }

    static HttpMessageConverter fastJsonHttpMessageConverterUTC() {
        return ChtHttpMessageConverterProvider.fastJsonHttpMessageConverterUTC();
    }

    static HttpMessageConverter fastJsonHttpMessageConverterTimestamp() {
        return ChtHttpMessageConverterProvider.fastJsonHttpMessageConverterTimestamp();
    }

    static HttpMessageConverter supportSpringDefaultHttpMessageConverterTimestamp() {
        return ChtHttpMessageConverterProvider.supportSpringDefaultHttpMessageConverterTimestamp();
    }
}
