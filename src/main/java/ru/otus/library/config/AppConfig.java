package ru.otus.library.config;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.otus.library.controller.LibraryConsoleBot;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.yml")
@AllArgsConstructor
public class AppConfig {

    private final Environment env;

    private static Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        String driverClassName = env.getProperty("driver-class-name");
        String url = env.getProperty("url");
        String username = env.getProperty("user");
        String password = env.getProperty("password");
        logger.info("Driver class name - " + driverClassName + ", url - " + url + ", username - " + username + ", password - " + password);

        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
