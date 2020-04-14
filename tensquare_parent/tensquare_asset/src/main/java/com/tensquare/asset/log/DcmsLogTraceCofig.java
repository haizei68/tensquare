package com.tensquare.asset.log;

import org.springframework.context.annotation.Bean;

public class DcmsLogTraceCofig {
    public DcmsLogTraceCofig(){

    }

    @Bean
    public DcmsLogAspect getLogAspect(){
        return new DcmsLogAspect();
    }
}
