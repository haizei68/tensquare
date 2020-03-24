package com.tensquare.asset;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import util.IdWorker;

@SpringBootApplication
@ComponentScan(basePackages = "com.tensquare")
@MapperScan("com.tensquare.asset.dao")
public class AssetApplication {
    private  static final Logger LOGGER = LoggerFactory.getLogger(AssetApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AssetApplication.class);
        LOGGER.info("============AssetApplication启动完成===============");

    }

    @Bean
    public IdWorker idWorker(){
        return   new IdWorker(1,2);
    }
}
