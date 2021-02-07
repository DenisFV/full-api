package ru.denisfv.fullapi.architecture.rsocket.server.controller.abstr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public abstract class AbstractTest {

    @Autowired
    protected WebTestClient client;
}
