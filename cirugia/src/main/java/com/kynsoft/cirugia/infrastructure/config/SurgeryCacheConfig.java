package com.kynsoft.cirugia.infrastructure.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class SurgeryCacheConfig {
    public static final String SURGERY_SERVICE_CACHE = "surgeryServiceCache";
}