package com.tensquare.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

@SpringBootApplication
public class BaseApplication {

    private  static final Logger LOGGER = LoggerFactory.getLogger(BaseApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class);
        LOGGER.info("============BaseApplication启动完成===============");
    }

    @Bean
    public IdWorker idWorker(){
      return   new IdWorker(1,2);
    }
}
