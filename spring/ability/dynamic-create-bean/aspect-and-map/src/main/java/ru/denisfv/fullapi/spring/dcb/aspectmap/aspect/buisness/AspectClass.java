package ru.denisfv.fullapi.spring.dcb.aspectmap.aspect.buisness;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Aspect
@PropertySource("classpath:aspect.properties")
public class AspectClass {

    @Value("${dynamic.bean.name}")
    private String dynamicBeanName;

    @Autowired
    private ApplicationContext context;

    @Pointcut("execution(@ru.denisfv.fullapi.spring.dcb.aspectmap.aspect.buisness.InjectDynamicBean * *(..))")
    public void beanAnnotatedWithInjectDynamicBean(){
    }

    @Around("beanAnnotatedWithInjectDynamicBean()")
    public Object adviceBeanAnnotatedWithInjectDynamicBean(ProceedingJoinPoint point) throws Throwable {
        point.proceed();

        return context.getBean(dynamicBeanName);
    }
}
