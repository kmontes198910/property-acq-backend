package com.kynsof.patients.infrastructure.services.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.queue.treatment.name}")
    private String treatmentQueue;

    @Value("${rabbitmq.queue.payment.name}")
    private String paymentQueue;

    @Value("${rabbitmq.queue.evaluation.name}")
    private String evaluationQueue;

    @Value("${rabbitmq.queue.identity.name}")
    private String identityQueue;

    @Value("${rabbitmq.queue.hospitalization.name}")
    private String hospitalIzationQueue;

    @Value("${rabbitmq.queue.calendar.name}")
    private String calendarQueue;

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(exchangeName);
    }

    @Bean
    public Queue treatmentQueue() {
        return new Queue(treatmentQueue, true); // Cola durable
    }

    @Bean
    public Queue paymentQueue() {
        return new Queue(paymentQueue, true);
    }

    @Bean
    public Queue evaluationQueue() {
        return new Queue(evaluationQueue, true);
    }

    @Bean
    public Queue identityQueue() {
        return new Queue(identityQueue, true);
    }

    @Bean
    public Queue hospitalIzationQueue() {
        return new Queue(hospitalIzationQueue, true);
    }

    @Bean
    public Queue calendarQueue() {
        return new Queue(calendarQueue, true);
    }

    @Bean
    public Binding bindTreatmentQueue(FanoutExchange exchange, Queue treatmentQueue) {
        return BindingBuilder.bind(treatmentQueue).to(exchange);
    }

    @Bean
    public Binding bindPaymentQueue(FanoutExchange exchange, Queue paymentQueue) {
        return BindingBuilder.bind(paymentQueue).to(exchange);
    }

    @Bean
    public Binding bindHospitalIzationQueue(FanoutExchange exchange, Queue hospitalIzationQueue) {
        return BindingBuilder.bind(hospitalIzationQueue).to(exchange);
    }

    @Bean
    public Binding bindIdentityQueue(FanoutExchange exchange, Queue identityQueue) {
        return BindingBuilder.bind(identityQueue).to(exchange);
    }

    @Bean
    public Binding bindCalendarQueue(FanoutExchange exchange, Queue calendarQueue) {
        return BindingBuilder.bind(calendarQueue).to(exchange);
    }
}