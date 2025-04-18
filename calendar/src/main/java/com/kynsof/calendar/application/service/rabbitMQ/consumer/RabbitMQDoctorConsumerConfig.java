package com.kynsof.calendar.application.service.rabbitMQ.consumer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQDoctorConsumerConfig {

    //Este valor debe de ser igual al micro que produce
    public static final String DOCTOR_EXCHANGE = "doctor.topic.exchange";

    //Este valor debe de ser igual al micro que produce
    public static final String DOCTOR_CREATED_ROUTING_KEY = "doctor.routing.key";

    //Este valor es unico para el micro.
    public static final String DOCTOR_QUEUE = "doctor.queue.calendar";

    @Bean
    public TopicExchange doctorExchange() {
        return new TopicExchange(DOCTOR_EXCHANGE);
    }

    // Cada microservicio debe usar un nombre de cola ÚNICO
    @Bean
    public Queue doctorCreatedQueue() {
        return new Queue(DOCTOR_QUEUE, true);
    }

    @Bean
    public Binding doctorCreatedBinding() {
        return BindingBuilder.bind(doctorCreatedQueue())
                           .to(doctorExchange())
                           .with(DOCTOR_CREATED_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
