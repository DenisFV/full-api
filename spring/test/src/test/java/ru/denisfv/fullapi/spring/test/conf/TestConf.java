package ru.denisfv.fullapi.spring.test.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.denisfv.fullapi.spring.test.service.MyService;

@Configuration
public class TestConf {

    @Bean
    public MyService myService() {
        return new MyService();
    }
}
