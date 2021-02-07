package ru.denisfv.fullapi.spring.starter.sse.main;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.Objects;

@Component
public class MainRunnerListener {

    @Autowired
    private ConfigurableListableBeanFactory factory;

    @SneakyThrows
    @EventListener
    public void handlerContextRefresh(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            BeanDefinition beanDefinition = factory.getBeanDefinition(name);
            String beanClassName = beanDefinition.getBeanClassName();
            if (Objects.isNull(beanClassName)) {
                continue;
            }
            Class<?> beanClass = ClassUtils.resolveClassName(beanClassName, ClassUtils.getDefaultClassLoader());
            Method[] methods = beanClass.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Main.class)) {
                    Object bean = context.getBean(name);
                    method.invoke(bean);
                }
            }
        }
    }
}
