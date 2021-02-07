package ru.denisfv.fullapi.spring.lifecycle.model;

import ru.denisfv.fullapi.spring.lifecycle.annotation.BeanFactory;
import ru.denisfv.fullapi.spring.lifecycle.annotation.MyProxy;
import ru.denisfv.fullapi.spring.lifecycle.annotation.PostProcessor;
import ru.denisfv.fullapi.spring.lifecycle.annotation.RefreshedEvent;

import javax.annotation.PostConstruct;

@MyProxy
@BeanFactory(newImpl = BeanClassWithFactoryBPP.class)
public class BeanClass implements BeanInterface {

    @PostProcessor(value = 1)
    private int postProcessor;

    private String beanField;

    public void setBeanField(String beanField) {
        this.beanField = beanField;
    }

    public BeanClass() {
        System.out.println("6. BeanFactory через рефлекшн создает бины классов через конструктор");
        System.out.println("~Конструктор");
        System.out.println("7. Заполняются поля бина, указанные в контексте");
        System.out.println("8. Отрабатываются before() всех BPP");
    }

    @PostConstruct
    public void init() {
        System.out.println("9. Выолняются init() всех бинов");
        System.out.println("~init()");
        System.out.println("10. Отрабатываются after() всех BPP");
    }

    @RefreshedEvent
    @Override
    public void refreshedEvent() {
        System.out.println("~refreshedEvent()");
    }

    @Override
    public void beanMethod() {
        System.out.println("~beanMethod()");
    }
}
