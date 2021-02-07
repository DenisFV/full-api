package ru.denisfv.fullapi.spring.context.annotation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
//@Lazy // создание бина не при запуске контекста, а когда мы его запрашиваем
//@DependsOn // дополнительно сканирует бины в указанном в скобках пакете
//@ComponentScan // дополнительно сканирует бины в указанном в скобках пакете
public class SingletonBeanClass implements BeanInterface {

    private String beanField;

    public void setBeanField(String beanField) {
        this.beanField = beanField;
    }

    @Override
    public void beanMethod() {
        System.out.println("beanMethod() in singleton bean");
    }
}
