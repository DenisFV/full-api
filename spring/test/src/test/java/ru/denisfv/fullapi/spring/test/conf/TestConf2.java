package ru.denisfv.fullapi.spring.test.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.denisfv.fullapi.spring.test.service.MyService2;

@Configuration
public class TestConf2 {

    @Bean
    public MyService2 myService2() {
        return new MyService2();
    }
}
