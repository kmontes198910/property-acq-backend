package com.kynsoft.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/payments-confirm")
public class PaymentController {
    private final RestTemplate restTemplate = new RestTemplate();


    @PostMapping("/status")
    public ResponseEntity<PaymentStatus> receivePaymentStatus(@RequestBody PaymentStatus paymentStatus) {
        String url = "http://localhost:5008/placetopay/transactions/notification";
        restTemplate.postForEntity(url, paymentStatus, Void.class);

        return ResponseEntity.ok(paymentStatus);
    }
}
