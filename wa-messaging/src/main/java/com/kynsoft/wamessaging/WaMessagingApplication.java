package com.kynsoft.wamessaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Aplicación principal del servicio de mensajería WhatsApp
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
public class WaMessagingApplication {

    public static void main(String[] args) {
        SpringApplication.run(WaMessagingApplication.class, args);
    }
}
