package com.denis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.denis.repository")
public class Application {
    public Application(){}

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
