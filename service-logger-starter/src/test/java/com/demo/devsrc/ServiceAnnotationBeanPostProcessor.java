package com.demo.devsrc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Пример логирования с использованием BeanPostProcessor
 */

@Slf4j
@Service
public class ServiceAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Service.class)) {
            return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    String arguments = Arrays.stream(args)
                            .filter(Objects::nonNull)
                            .map(String::valueOf)
                            .collect(Collectors.joining(", "));
                    log.info(String.format("%s args: [ %s ]", method.getName(), arguments));
                    return method.invoke(bean, args);
                }
            });
        }
        return bean;
    }
}
