package ru.denisfv.fullapi.spring.lifecycle.bpp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import ru.denisfv.fullapi.spring.lifecycle.annotation.RefreshedEvent;

import java.lang.reflect.Method;

public class RefreshedEventAL implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ConfigurableListableBeanFactory factory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("11. Отрабатываются ApplicationListener для refreshed event");
        System.out.println("~RefreshedEventAL");
        ApplicationContext context = event.getApplicationContext();
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            BeanDefinition beanDefinition = factory.getBeanDefinition(name);
            String originalClassName = beanDefinition.getBeanClassName();

            try {
                Class<?> originalClass = Class.forName(originalClassName);
                Method[] methods = originalClass.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RefreshedEvent.class)) {
//                        if (isCGLIB) {
//                            method.invoke(factory.getBean(name));
//                        } else {
                            Object bean = context.getBean(name);
                            Method curMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                            curMethod.invoke(bean);
//                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
