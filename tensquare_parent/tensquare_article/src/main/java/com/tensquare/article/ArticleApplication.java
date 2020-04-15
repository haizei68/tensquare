package com.tensquare.article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;
@SpringBootApplication
@EnableEurekaClient
public class ArticleApplication {

	private  static final Logger LOGGER = LoggerFactory.getLogger(ArticleApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(ArticleApplication.class, args);
		LOGGER.info("============BaseApplication启动完成===============");
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker(1, 1);
	}
	
}
