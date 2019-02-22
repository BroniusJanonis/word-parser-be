package com.wordparser.demo.configurator;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfiguration {

    public static final String DOCUMENT_DOWNLOAD_CACHE = "documentDownload";

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();

        GuavaCache cache1 = new GuavaCache(DOCUMENT_DOWNLOAD_CACHE, CacheBuilder.newBuilder()
                .maximumSize(1)
                .expireAfterWrite(3, TimeUnit.HOURS)
                .build());
        simpleCacheManager.setCaches(Arrays.asList(cache1));
        return simpleCacheManager;
    }
}