package com.project;

import com.project.web.configs.ServiceVersionProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableConfigurationProperties(value = {ServiceVersionProperties.class})
@EnableTransactionManagement
public class AutomaticEntrySystemApplication implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        SpringApplication.run(AutomaticEntrySystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
