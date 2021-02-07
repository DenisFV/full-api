package ru.denisfv.fullapi.spring.upis.bad;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.awt.*;
import java.util.Random;

@ComponentScan
public class Config {

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public Color color() {
        Random random = new Random();
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }
}
