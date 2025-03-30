package com.kynsof.treatments.application.service.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String EXCHANGE_NAME = "paciente.exchange";
    private static final String TREATMENT_QUEUE = "paciente.treatment";

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue treatmentQueue() {
        return new Queue(TREATMENT_QUEUE, true);
    }

    @Bean
    public Binding bindTreatmentQueue(Queue treatmentQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(treatmentQueue).to(fanoutExchange);
    }
}