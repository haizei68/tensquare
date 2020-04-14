package com.tensquare.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmsApplication {
    private  static final Logger LOGGER = LoggerFactory.getLogger(SmsApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class, args);
        LOGGER.info("============SmsApplication启动完成===============");
    }

}
