package com.kynsoft.cirugia.infrastructure.services.rabbitMQ.consumer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConsumerPatientConfig {

    //Este valor debe de ser igual al micro que produce
    public static final String PATIENT_EXCHANGE = "patient.topic.exchange";

    //Este valor debe de ser igual al micro que produce
    public static final String PATIENT_CREATED_ROUTING_KEY = "patient.routing.key";

    //Este valor es unico para el micro.
    public static final String PATIENT_QUEUE = "patient.queue.cirugia";

    @Bean
    public TopicExchange patientExchange() {
        return new TopicExchange(PATIENT_EXCHANGE);
    }

    // Cada microservicio debe usar un nombre de cola ÚNICO
    @Bean
    public Queue patientCreatedQueue() {
        return new Queue(PATIENT_QUEUE, true);
    }

    @Bean
    public Binding patientCreatedBinding() {
        return BindingBuilder.bind(patientCreatedQueue())
                           .to(patientExchange())
                           .with(PATIENT_CREATED_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
