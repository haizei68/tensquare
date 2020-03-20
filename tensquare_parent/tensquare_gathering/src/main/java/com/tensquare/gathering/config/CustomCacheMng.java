package com.tensquare.gathering.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Configuration
public class CustomCacheMng{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 自定义的cacheManager，实现存活2天
    @Bean(name = "customCacheManager")
    public CacheManager cacheManager(
            RedisTemplate<?, ?> redisTemplate) {
        RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(redisTemplate.getConnectionFactory());
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(1));
        return new RedisCacheManager(writer, config);
    }

    // 提供默认的cacheManager，应用于全局
    @Bean
    @Primary
    public CacheManager defaultCacheManager(
            RedisTemplate<?, ?> redisTemplate) {
        RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(redisTemplate.getConnectionFactory());
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        return new RedisCacheManager(writer, config);
    }
}
