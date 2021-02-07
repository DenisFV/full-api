package ru.denisfv.fullapi.architecture.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.denisfv.fullapi.architecture.mvc.config.RedisConfig;
import ru.denisfv.fullapi.architecture.mvc.util.QuerySupportService;
import ru.denisfv.fullapi.architecture.mvc.util.RSQLService;

@Configuration
public class MVCArchitectureConfiguration {

    @Bean
    public RedisConfig redisConfig() {
        return new RedisConfig();
    }

    @Bean
    public QuerySupportService querySupportService() {
        return new QuerySupportService<>();
    }

    @Bean
    public RSQLService rsqlService() {
        return new RSQLService();
    }
}
