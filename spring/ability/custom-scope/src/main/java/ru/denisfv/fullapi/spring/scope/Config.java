package ru.denisfv.fullapi.spring.scope;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.UUID;

@Configuration
@ComponentScan
public class Config {

    @Bean
    @Scope("periodical")
    public int beanField() {
        return new Random().nextInt(1000);
    }

    @Bean
    public BeanClass frame() {
        return new BeanClass() {
            @Override
            protected int getBeanField() {
                return beanField();
            }
        };
    }
}
