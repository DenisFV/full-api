package ru.denisfv.fullapi.spring.test.conf;

import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.denisfv.fullapi.spring.test.controller.MyController;
import ru.denisfv.fullapi.spring.test.service.MyDBService;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Configuration
public class ControllerTestConf {

    @MockBean
    MyDBService service;

    @PostConstruct
    public void myDBServiceInit() {
        Mockito.doReturn(Collections.singletonList("aaa"))
                .when(service).findAll();
    }

    @Bean
    public MyController myController() {
        return new MyController();
    }
}
