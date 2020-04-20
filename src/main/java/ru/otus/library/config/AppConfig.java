package ru.otus.library.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.yml")
@AllArgsConstructor
public class AppConfig {

    private static Logger logger = LoggerFactory.getLogger(AppConfig.class);
    private static final String CHANGELOGS_PACKAGE = "ru.otus.library.changelog";

    private final Environment env;
    private final MongoClient mongo;

    @Bean
    public Mongock mongock(Environment env, MongoClient mongoClient) {
        logger.info("database is " + env.getProperty("database"));
        return new SpringMongockBuilder(mongoClient, env.getProperty("database"), CHANGELOGS_PACKAGE)
                .build();
    }
}
