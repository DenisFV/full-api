package ru.denisfv.fullapi.spring.lifecycle.model;

import ru.denisfv.fullapi.spring.lifecycle.annotation.RefreshedEvent;

public class BeanClassWithFactoryBPP extends BeanClass implements BeanInterface {

    @RefreshedEvent
    @Override
    public void refreshedEvent() {
        System.out.println("~refreshedEvent() with factory BPP");
    }

    @Override
    public void beanMethod() {
        System.out.println("~beanMethod() with factory BPP");
    }
}
