package ru.denisfv.fullapi.jmx.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;

@SpringBootApplication
@EnableMBeanExport
public class JMXSpringBootApplication implements CommandLineRunner {

    @Autowired
    JMXSpringBootController controller;

    public static void main(String[] args) {
        SpringApplication.run(JMXSpringBootApplication.class, args);
    }

    public void run(String... args) throws Exception {
        while (true) {
            Thread.sleep(1000);
            System.out.println(controller.isEnabled());
        }
    }
}
