package com.tensquare.spit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

@SpringBootApplication
public class SpitApplication {
    private  static final Logger LOGGER = LoggerFactory.getLogger(SpitApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpitApplication.class);
        LOGGER.info("============SpitApplication启动完成===============");
    }

    @Bean
    public IdWorker idWorker(){
        return   new IdWorker(1,2);
    }
}
