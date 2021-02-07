package ru.denisfv.fullapi.spring.test.conf;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.denisfv.fullapi.spring.test.repo.MyRepo;
import ru.denisfv.fullapi.spring.test.service.MyDBService;

@Configuration
public class DBTestConf {

    @MockBean
    MyRepo myRepo;

    @Bean
    public MyDBService myDBService() {
        return new MyDBService();
    }
}
