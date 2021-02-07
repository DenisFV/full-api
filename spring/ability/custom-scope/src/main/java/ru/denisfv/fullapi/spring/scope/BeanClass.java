package ru.denisfv.fullapi.spring.scope;

public abstract class BeanClass {

    protected abstract int getBeanField();

    public void printBeanField() {
        System.out.println(getBeanField());
    }
}
