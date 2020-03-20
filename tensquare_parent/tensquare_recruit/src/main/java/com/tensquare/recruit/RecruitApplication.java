package com.tensquare.recruit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.IdWorker;

@SpringBootApplication
public class RecruitApplication {
    private  static final Logger LOGGER = LoggerFactory.getLogger(RecruitApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RecruitApplication.class);
        LOGGER.info("============SpitApplication启动完成===============");
    }

    @Bean
    public IdWorker idWorker(){
        return   new IdWorker(1,2);
    }
}

