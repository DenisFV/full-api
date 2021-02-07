package ru.denisfv.fullapi.architecture.rsocket.client.abstr;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
//@AutoConfigureWebFlux
@ActiveProfiles("test")
public abstract class AbstractTest {

    @NonNull
    @Autowired
    protected WebTestClient client;
}
