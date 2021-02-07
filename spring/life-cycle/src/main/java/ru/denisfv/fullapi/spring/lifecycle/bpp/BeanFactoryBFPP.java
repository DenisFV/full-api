package ru.denisfv.fullapi.spring.lifecycle.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import ru.denisfv.fullapi.spring.lifecycle.annotation.BeanFactory;


public class BeanFactoryBFPP implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("4. BeanFactoryPostProcessor проходит по всем BeanDefinition и вносит в них изменения");
        System.out.println("~BeanFactoryBFPP");
        String[] names = beanFactory.getBeanDefinitionNames();
        for (String name : names) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
            String beanClassName = beanDefinition.getBeanClassName();

            try {
                Class<?> beanClass = Class.forName(beanClassName);
                BeanFactory ann = beanClass.getAnnotation(BeanFactory.class);
                if (ann != null) {
                    beanDefinition.setBeanClassName(ann.newImpl().getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("5. BeanFactory начинает создание BeanPostProcessor-ов (before и after)");
    }
}
