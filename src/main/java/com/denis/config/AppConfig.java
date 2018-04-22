package com.denis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@ComponentScan(basePackages = {"com.denis"})
@Import({SecurityConfig.class, SpringDataConfig.class})
public class AppConfig {


}