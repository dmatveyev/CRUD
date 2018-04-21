package com.denis.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.denis"})
@Import({OrmConfig.class, WebConfig.class, SecurityConfig.class, SpringDataConfig.class})
public class AppConfig {
}