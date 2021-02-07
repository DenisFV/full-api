package ru.denisfv.fullapi.spring.starter.sse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.denisfv.fullapi.spring.starter.sse.main.MainBPP;
import ru.denisfv.fullapi.spring.starter.sse.main.MainRunnerListener;

@Configuration
public class StarterConfig {

    @Bean
    public MainRunnerListener mainRunnerListener(){
        return new MainRunnerListener();
    }

    @Bean
    public MainBPP mainBPP(){
        return new MainBPP();
    }
}
