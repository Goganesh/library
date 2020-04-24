package ru.otus.library;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.library.config", "ru.otus.library.dao"})
//@Import(RawResultPrinterImpl.class)
public abstract class AbstractRepositoryTest {
}
