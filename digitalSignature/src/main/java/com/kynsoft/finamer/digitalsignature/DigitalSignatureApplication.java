package com.kynsoft.finamer.digitalsignature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DigitalSignatureApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalSignatureApplication.class, args);
    }
}
