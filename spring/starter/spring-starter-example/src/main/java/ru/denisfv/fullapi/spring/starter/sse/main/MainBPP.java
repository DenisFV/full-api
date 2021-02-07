package ru.denisfv.fullapi.spring.starter.sse.main;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Stream;

@Component
public class MainBPP implements BeanPostProcessor {

    @Autowired
    private ConfigurableListableBeanFactory factory;

    private boolean mainAnnotationFilter(Object bean) {
        return Stream.of(bean.getClass().getMethods()).anyMatch(method -> method.isAnnotationPresent(Main.class));
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Stream.of(factory.getBeanDefinitionNames()).forEach(name -> {
            if (mainAnnotationFilter(bean)) {
                BeanDefinition beanDefinition = factory.getBeanDefinition(beanName);
                if (Objects.isNull(beanDefinition.getBeanClassName())) {
                    beanDefinition.setBeanClassName(bean.getClass().getCanonicalName());
                }

            }
        });
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
