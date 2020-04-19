package ru.otus.library.config;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import ru.otus.library.config.changelog.DatabaseChangelog;

@Configuration
@PropertySource("classpath:application.yml")
@AllArgsConstructor
public class AppConfig {

    private static Logger logger = LoggerFactory.getLogger(AppConfig.class);

    private final Environment env;
    private final MongoClient mongo;

   @Bean
    public Mongobee mongobee(Environment environment) {
        Mongobee runner = new Mongobee(mongo);
        runner.setDbName("library");
        runner.setChangeLogsScanPackage(DatabaseChangelog.class.getPackage().getName());
        runner.setSpringEnvironment(environment);
        return runner;
    }
}
