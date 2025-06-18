package com.kynsof.hospitalizationService.infrastructure.service.rabbitMQ.consumer;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConsumerPatientsDeleteConfig {
    //Este valor debe de ser igual al micro que produce
    public static final String PATIENT_DELETE_EXCHANGE = "patient.delete.topic.exchange";

    //Este valor debe de ser igual al micro que produce
    public static final String PATIENT_DELETE_ROUTING_KEY = "patient.routing.key";

    //Este valor es unico para el micro.
    public static final String PATIENT_DELETE_QUEUE = "patient.delete.queue.hospitalization";

    @Bean
    public TopicExchange patientDeleteExchange() {
        return new TopicExchange(PATIENT_DELETE_EXCHANGE);
    }

    // Cada microservicio debe usar un nombre de cola ÚNICO
    @Bean
    public Queue patientDeleteQueue() {
        return new Queue(PATIENT_DELETE_QUEUE, true);
    }

    @Bean
    public Binding patientDeleteBinding() {
        return BindingBuilder.bind(patientDeleteQueue())
                .to(patientDeleteExchange())
                .with(PATIENT_DELETE_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
