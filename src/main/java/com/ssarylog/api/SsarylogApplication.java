package com.ssarylog.api;

import com.ssarylog.api.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class SsarylogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsarylogApplication.class, args);
    }

}
