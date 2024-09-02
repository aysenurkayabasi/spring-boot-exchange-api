package com.openpayd.exchange.config;


import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CacheConfig {

    public static final String EXCHANGE_RATE_CACHE = "exchangeRateCache";

    public static final String EXCHANGE_LIST_CACHE = "exchangeListCache";

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeineSpec());
        return cacheManager;
    }

    @Bean
    public Caffeine<Object, Object> caffeineSpec() {
        return Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES);
    }

    @Bean
    public Cache exchangeRateCache() {
        return new CaffeineCache(EXCHANGE_RATE_CACHE, Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build());
    }

    @Bean
    public Cache exchangeListCache() {
        return new CaffeineCache(EXCHANGE_LIST_CACHE, Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.DAYS)
                .build());
    }
}
