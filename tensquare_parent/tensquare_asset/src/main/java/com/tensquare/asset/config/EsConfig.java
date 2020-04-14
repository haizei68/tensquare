package com.tensquare.asset.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.image.RescaleOp;

@Configuration
public class EsConfig {
    private static final Logger LOGGER= LoggerFactory.getLogger(EsConfig.class);
    @Value("${es.url}")
    private String url;
    String userName;
    String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Bean
    public RestClient getLowLevelClient(){
        String[] urls = this.getUrl().split(",");
        HttpHost[] hosts = new HttpHost[urls.length];
        int i=0 ;
        for (String url : urls) {
            String []parts = url.split(":");
            hosts[i] = new HttpHost(parts[0], Integer.valueOf(parts[1]), "http");
        }

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        //credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials(this.getUserName(),getPassword()));

        RestClient client = RestClient.builder(hosts).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        }).build();
        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
                try{
                    LOGGER.info("shutdown es restclient");
                    client.close();
                    LOGGER.info("shut down es restclient finish");
                }catch (Exception e){
                    LOGGER.info("shutdown es restclient fail",e);
                }
            }
        });
        return client;
    }

    @Bean
    public RestHighLevelClient gethighLevelClient(RestClient restClient){
        return  new RestHighLevelClient(restClient);
    }
}
