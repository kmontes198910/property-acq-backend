package com.kynsof.identity.controller;

import com.kynsof.identity.infrastructure.services.rabbitMq.otp.OtpMessageProducer;
import com.kynsof.identity.infrastructure.services.rabbitMq.welcome.WelcomeMessageProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rabbitmq")
public class RabbitMQController {

    private final WelcomeMessageProducer producer;
    private final OtpMessageProducer otpProducer;

    public RabbitMQController(WelcomeMessageProducer producer, OtpMessageProducer otpProducer) {
        this.producer = producer;
        this.otpProducer = otpProducer;
    }

    @GetMapping("/welcome")
    public String sendWelcomeMessage(
            @RequestParam String email,
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam String password) {

        producer.sendWelcomeMessage(email, firstname, lastname, password);
        return "Mensaje de bienvenida enviado a RabbitMQ";
    }

    @GetMapping("/otp")
    public String sendOtpMessage(
            @RequestParam String email,
            @RequestParam String otp,
            @RequestParam String name) {

        otpProducer.sendOtpMessage(email, otp,name);
        return "Mensaje de bienvenida enviado a RabbitMQ";
    }
}