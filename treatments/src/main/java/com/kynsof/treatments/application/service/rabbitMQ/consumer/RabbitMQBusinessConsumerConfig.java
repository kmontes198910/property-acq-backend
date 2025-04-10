package com.kynsof.treatments.application.service.rabbitMQ.consumer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQBusinessConsumerConfig {

    //Este valor debe de ser igual al micro que produce
    @Value("${rabbitmq.exchange.business}")
    public String BUSINESS_EXCHANGE;

    //Este valor debe de ser igual al micro que produce
    @Value("${rabbitmq.routing.key.business}")
    public String BUSINESS_CREATED_ROUTING_KEY;

    //Este valor es unico para el micro.
    @Value("${rabbitmq.queues.business}")
    public String BUSINESS_QUEUE;

    @Bean
    public TopicExchange businessExchange() {
        return new TopicExchange(BUSINESS_EXCHANGE);
    }

    // Cada microservicio debe usar un nombre de cola ÚNICO
    @Bean
    public Queue businessCreatedQueue() {
        return new Queue(BUSINESS_QUEUE, true);
    }

    @Bean
    public Binding businessCreatedBinding() {
        return BindingBuilder.bind(businessCreatedQueue())
                           .to(businessExchange())
                           .with(BUSINESS_CREATED_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
