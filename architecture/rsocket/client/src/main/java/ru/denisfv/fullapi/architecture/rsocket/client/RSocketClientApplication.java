package ru.denisfv.fullapi.architecture.rsocket.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class RSocketClientApplication {

    public static void main(String[] args) {
        log.info("ArchitectureApplication starting");
        SpringApplication.run(RSocketClientApplication.class, args);
    }
}
