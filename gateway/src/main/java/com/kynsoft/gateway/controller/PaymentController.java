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
        //String url = "http://localhost:5008/placetopay/transactions/notification";
        try {
            System.err.println("Notifica place to pay:"+paymentStatus.getStatus().getStatus());
            String url = "http://payment:8080/placetopay/transactions/notification";
            restTemplate.postForEntity(url, paymentStatus, Void.class);
        }catch (Exception e) {
            System.err.println("Error al notificar al servicio de payment de place to pay: " + e.getMessage());
        }


        // Call the notification change status endpoint
        try {
            int requestId = paymentStatus.getRequestId(); // assuming you have this field
            String notifyUrl = "http://payment-internal-service:9901/notification-change-status/" + requestId;
            restTemplate.getForEntity(notifyUrl, Void.class);
        } catch (Exception e) {
            System.err.println("Error al notificar en el servicio de group payment");
            System.err.println(e.getMessage());
        }

        return ResponseEntity.ok(paymentStatus);
    }
}
