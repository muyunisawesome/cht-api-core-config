package com.cht.core.api.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class BeanFactoryUtil implements BeanFactoryAware {
    private static BeanFactory beanFactory;

    public BeanFactoryUtil() {
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        BeanFactoryUtil.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public static Object getBean(String id) {
        return beanFactory.getBean(id);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return beanFactory.getBean(requiredType);
    }
}