package ru.denisfv.fullapi.architecture.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class MVCArchitectureApplication {

    public static void main(String[] args) {
        log.info("ArchitectureApplication starting");
        SpringApplication.run(MVCArchitectureApplication.class, args);
    }
}
