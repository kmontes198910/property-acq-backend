package com.kynsof.treatments.application.service.rabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String TREATMENT_QUEUE = "paciente.treatment";
    private static final String EXCHANGE_NAME = "paciente.exchange";

    @Bean
    public Queue treatmentQueue() {
        return new Queue(TREATMENT_QUEUE, true);
    }

    @Bean
    public FanoutExchange pacienteExchange() {
        return new FanoutExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public Binding bindTreatmentQueue(Queue treatmentQueue, FanoutExchange pacienteExchange) {
        return BindingBuilder.bind(treatmentQueue).to(pacienteExchange);
    }
}