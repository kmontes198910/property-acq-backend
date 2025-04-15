package com.kynsoft.rrhh.infrastructure.services.rabbitMQ;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQDoctorConfig {

    public static final String DOCTOR_EXCHANGE = "doctor.topic.exchange";
    public static final String DOCTOR_CREATED_ROUTING_KEY = "doctor.routing.key";

    //Doctor
    @Bean
    public TopicExchange doctorExchange() {
        return new TopicExchange(DOCTOR_EXCHANGE);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
