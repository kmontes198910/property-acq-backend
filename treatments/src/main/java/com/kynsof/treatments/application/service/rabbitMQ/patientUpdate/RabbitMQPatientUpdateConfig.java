package com.kynsof.treatments.application.service.rabbitMQ.patientUpdate;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQPatientUpdateConfig {

    private static final String TREATMENT_QUEUE = "paciente.update.treatment";
    private static final String EXCHANGE_NAME = "paciente.update.exchange";

    @Bean
    public Queue treatmentQueue() {
        return new Queue(TREATMENT_QUEUE, true);
    }
    @Bean
    public FanoutExchange pacienteExchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding bindTreatmentQueue(Queue treatmentQueue, FanoutExchange pacienteExchange) {
        return BindingBuilder.bind(treatmentQueue).to(pacienteExchange);
    }
}