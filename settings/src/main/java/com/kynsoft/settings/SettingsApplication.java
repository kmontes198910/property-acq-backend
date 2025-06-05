package com.kynsoft.settings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SettingsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SettingsApplication.class, args);
    }
}
