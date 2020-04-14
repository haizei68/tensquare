package com.tensquare.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

@SpringBootApplication
public class UserApplication {
    private  static final Logger LOGGER = LoggerFactory.getLogger(UserApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
        LOGGER.info("============UserApplication启动完成===============");
    }

    @Bean
    public IdWorker idWorkker(){
        return new IdWorker(1, 1);
    }
}
