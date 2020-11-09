package com.cht.core.api.config;

import com.cht.core.api.config.adapter.AppGlobalWebMvcConfigurerAdapter;
import com.cht.core.api.config.handler.AppGlobalExceptionHandler;
import com.cht.core.api.util.BeanFactoryUtil;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class ApiCoreConfig implements ImportSelector {
    public ApiCoreConfig() {
    }

    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{AppGlobalWebMvcConfigurerAdapter.class.getName(),
                AppGlobalExceptionHandler.class.getName(), BeanFactoryUtil.class.getName()};
    }
}