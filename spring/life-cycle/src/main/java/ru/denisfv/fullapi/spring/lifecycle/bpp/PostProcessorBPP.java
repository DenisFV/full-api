package ru.denisfv.fullapi.spring.lifecycle.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;
import ru.denisfv.fullapi.spring.lifecycle.annotation.PostProcessor;

import java.lang.reflect.Field;

public class PostProcessorBPP implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("~PostProcessorBPP before() for '" + beanName + "'");

        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            PostProcessor ann = field.getAnnotation(PostProcessor.class);
            if (ann != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, ann.value());
                System.out.println("-@PostProcessor");
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("~PostProcessorBPP after() for '" + beanName + "'");

        return bean;
    }
}
