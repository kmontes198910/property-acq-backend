package com.kynsof.payment.application.service.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String EXCHANGE_NAME = "paciente.exchange";
    private static final String PAYMENT_QUEUE_NAME = "paciente.payment";

    @Bean
    public Queue paymentQueue() {
        return new Queue(PAYMENT_QUEUE_NAME, true); // Cola durable
    }

    @Bean
    public FanoutExchange pacienteExchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingPaymentQueue(Queue paymentQueue, FanoutExchange pacienteExchange) {
        return BindingBuilder.bind(paymentQueue).to(pacienteExchange);
    }
}