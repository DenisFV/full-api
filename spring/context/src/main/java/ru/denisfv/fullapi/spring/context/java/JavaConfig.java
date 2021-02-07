package ru.denisfv.fullapi.spring.context.java;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

public class JavaConfig {

    @Bean
    public SingletonBeanClass singletonBean() {
        return new SingletonBeanClass();
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public PrototypeBeanClass prototypeBean() {
        return new PrototypeBeanClass();
    }
}
