package com.vocadabulary;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication
public class VocadabularyApplication {

    @Bean
public CommandLineRunner showMappings(RequestMappingHandlerMapping handlerMapping) {
    return args -> {
        handlerMapping.getHandlerMethods().forEach((mapping, method) -> {
            System.out.println(mapping + " => " + method);
        });
    };
    }
    public static void main(String[] args) {
        SpringApplication.run(VocadabularyApplication.class, args);
    }
}
