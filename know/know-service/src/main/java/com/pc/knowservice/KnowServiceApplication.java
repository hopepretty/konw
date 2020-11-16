package com.pc.knowservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class KnowServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowServiceApplication.class, args);
    }

}
