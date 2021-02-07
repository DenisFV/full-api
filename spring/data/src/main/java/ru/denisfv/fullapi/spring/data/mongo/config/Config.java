package ru.denisfv.fullapi.spring.data.mongo.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("ru.denisfv.fullapi.spring.data.mongo")
@ComponentScan("ru.denisfv.fullapi.spring.data.mongo")
public class Config {

    @Bean
    public MongoClient mongoClient() {
        return new MongoClient();
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "db_mongo");
    }
}
