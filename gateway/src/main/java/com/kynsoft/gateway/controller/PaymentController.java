package com.kynsoft.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
