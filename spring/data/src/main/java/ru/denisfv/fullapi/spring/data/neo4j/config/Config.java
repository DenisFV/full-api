//package ru.denisfv.fullapi.spring.data.neo4j.config;
//
//import org.neo4j.ogm.config.Configuration.Builder;
//import org.neo4j.ogm.session.SessionFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@Configuration
//@EnableNeo4jRepositories(basePackages = "ru.denisfv.fullapi.spring.data.neo4j")
////@ComponentScan("ru.denisfv.fullapi.spring.data.neo4j")
//@EnableTransactionManagement
//public class Config extends NeConf{
//
//    @Bean
//    public org.neo4j.ogm.config.Configuration getConfiguration() {
//        return new Builder().uri("http://neo4j:movies@localhost:7474").build();
////        return new GraphDatabaseFactory().newEmbeddedDatabase("neo4j.db");
//    }
//
//    @Bean
//    public SessionFactory getSessionFactory() {
//        return new SessionFactory(getConfiguration(), "ru.denisfv.fullapi.spring.data.neo4j.entity");
//    }
//}
