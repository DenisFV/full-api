package ru.denisfv.fullapi.spring.dcb.aspectmap.aspect.model;

import org.springframework.stereotype.Component;

@Component("beanOne")
public class BeanOne implements BeanInterface {

    @Override
    public String getName() {
        return "Bean One";
    }
}
