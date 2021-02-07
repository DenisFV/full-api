package ru.denisfv.fullapi.spring.dcb.aspectmap.aspect.model;

import org.springframework.stereotype.Component;

@Component("beanTwo")
public class BeanTwo implements BeanInterface {

    @Override
    public String getName() {
        return "Bean Two";
    }
}
