package com.kynsof.patients.infrastructure.services.rabbitMQ.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQPatientConfig {

    public static final String PATIENT_EXCHANGE = "patient.topic.exchange";
    public static final String PATIENT_CREATED_ROUTING_KEY = "patient.routing.key";

    //Patient
    @Bean
    public TopicExchange patientExchange() {
        return new TopicExchange(PATIENT_EXCHANGE);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
