package com.kynsof.hospitalizationService.infrastructure.service.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String EXCHANGE_NAME = "paciente.exchange";
    private static final String HOSPITALIZATION_QUEUE = "paciente.hospitalization";

    @Bean
    public Queue hospitalizationQueue() {
        return new Queue(HOSPITALIZATION_QUEUE, true); // Cola durable
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public Binding bindHospitalizationQueue(Queue hospitalizationQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(hospitalizationQueue).to(fanoutExchange);
    }
}