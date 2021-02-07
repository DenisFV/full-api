package ru.denisfv.fullapi.spring.starter.sst.boot;

import org.springframework.stereotype.Service;
import ru.denisfv.fullapi.spring.starter.sse.main.Main;

@Service
public class TestService {

    @Main
    public void testMain(){
        System.out.println("@Main is working...");
    }
}
