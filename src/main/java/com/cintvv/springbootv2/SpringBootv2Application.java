package com.cintvv.springbootv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SpringBootv2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootv2Application.class, args);
    }

}
