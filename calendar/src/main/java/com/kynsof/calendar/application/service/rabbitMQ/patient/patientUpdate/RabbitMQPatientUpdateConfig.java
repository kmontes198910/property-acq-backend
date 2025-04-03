package com.kynsof.calendar.application.service.rabbitMQ.patient.patientUpdate;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQPatientUpdateConfig {

    private static final String QUEUE_NAME = "paciente.update.calendar";
    private static final String EXCHANGE_NAME = "paciente.update.exchange";

    @Bean
    public Queue calendarnQueue() {
        return new Queue(QUEUE_NAME, true); // Cola durable
    }

    @Bean
    public FanoutExchange pacienteExchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue calendarnQueue, FanoutExchange pacienteExchange) {
        return BindingBuilder.bind(calendarnQueue).to(pacienteExchange);
    }
}