package com.square.spit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpitConfig {

    @Value("${mongodb.spitdb.database01}")
    private  String spit;

    public String getSpit() {
        return spit;
    }

    public void setSpit(String spit) {
        this.spit = spit;
    }
}
