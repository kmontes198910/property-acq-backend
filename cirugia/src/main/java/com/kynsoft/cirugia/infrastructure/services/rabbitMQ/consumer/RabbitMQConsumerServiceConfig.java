package com.kynsoft.cirugia.infrastructure.services.rabbitMQ.consumer;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConsumerServiceConfig {
    //Este valor debe de ser igual al micro que produce
    public static final String EXCHANGE_NAME = "service.exchange";

    //Este valor debe de ser igual al micro que produce
    public static final String SERVICE_CREATED_ROUTING_KEY = "service.routing.key";

    //Este valor es unico para el micro.
    public static final String SERVICE_QUEUE = "service.queue.cirugia";

    @Bean
    public TopicExchange serviceExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // Cada microservicio debe usar un nombre de cola ÚNICO
    @Bean
    public Queue serviceQueue() {
        return new Queue(SERVICE_QUEUE, true);
    }

    @Bean
    public Binding ServiceCreatedBinding() {
        return BindingBuilder.bind(serviceQueue())
                .to(serviceExchange())
                .with(SERVICE_CREATED_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
