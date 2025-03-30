package com.kynsof.calendar.application.service.rabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String QUEUE_NAME = "paciente.calendar";
    private static final String EXCHANGE_NAME = "paciente.exchange";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true); // Cola durable
    }

    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }
}