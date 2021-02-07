package ru.denisfv.fullapi.architecture.rsocket.server.router.abstr;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class AbstractTest {

    @NonNull
    @Autowired
    protected RSocketRequester requester;

//    @BeforeAll
//    public static void setUp(
//            @Autowired RSocketRequester.Builder build,
//            @Value("${server.address}") String address,
//            @LocalRSocketServerPort Integer port
//    ) {
//        builder = build;
//                .rsocketConnector(conn  -> conn
//                        .reconnect(Retry.fixedDelay(Integer.MAX_VALUE, Duration.ofSeconds(1))))
//                .tcp(address, port);
//    }
}
