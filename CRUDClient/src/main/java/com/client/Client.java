package com.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.client.repository")
public class Client {
    public static void main(final String[] args) {
        SpringApplication.run(Client.class, args);
    }
}
