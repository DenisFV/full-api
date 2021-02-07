package ru.denisfv.fullapi.architecture.rsocket.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;

@Slf4j
@Configuration
public class RSocketConfiguration {

    @Bean
    RSocketRequester rSocketRequester(
            RSocketRequester.Builder builder,
            @Value("${server.address}") String address,
            @Value("${spring.rsocket.server.port}") Integer port
    ) {
        return builder
                .tcp(address, port);
    }
}
