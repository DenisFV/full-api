package ru.denisfv.fullapi.architecture.rsocket.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
@Slf4j
@EnableCaching
public class RSocketServerApplication {

    public static void main(String[] args) {
        ReactorDebugAgent.init();

        log.info("ArchitectureApplication starting");
        SpringApplication.run(RSocketServerApplication.class, args);
    }
}
