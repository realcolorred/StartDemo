package com.example.gateway.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Spring上下文管理器
 *
 */
@Component
public class SpringBeanUtils implements ApplicationContextAware {

    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtils.context = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> requiredType) {
        String className = getAnnotation(requiredType);
        if (className != null && !"".equals(className)) {
            if (context.containsBean(className)) {
                return (T) context.getBean(className);
            }
        }
        if (context.containsBean(requiredType.getName())) {
            return (T) context.getBean(requiredType.getName());
        }
        return context.getBean(requiredType);
    }

    public static Object getBean(String className) {
        return context.getBean(className);
    }

    public static Object getBean(String className, Object... args) {
        return context.getBean(className, args);
    }

    public static <T> Map<String, T> getBeans(Class<T> requiredType) {
        return context.getBeansOfType(requiredType);
    }

    private static String getAnnotation(Class<?> type) {
        if (type.isAnnotationPresent(Repository.class)) {
            Repository an = (Repository) type.getAnnotation(Repository.class);
            return an.value();
        }
        //如果没有注解，则默认把第一个字母置为小写即可。
        String simpleName = type.getSimpleName();
        if (simpleName != null && !"".equals(simpleName)) {
            String firstS = simpleName.substring(0, 1);
            firstS = firstS.toLowerCase();
            String nextStr = "";
            if (simpleName.length() > 1) {
                nextStr = simpleName.substring(1);
            }
            return firstS + nextStr;
        }

        return "";
    }

    public static ConfigurableEnvironment getEnvironment() {
        return (ConfigurableEnvironment) context.getEnvironment();
    }

}
