package com.kynsof.hospitalizationService.infrastructure.service.rabbitMQ.consumer;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConsumerServiceTypeConfig {
    //Este valor debe de ser igual al micro que produce
    public static final String EXCHANGE_NAME = "service-type.exchange";

    //Este valor debe de ser igual al micro que produce
    public static final String SERVICE_TYPE_CREATED_ROUTING_KEY = "service.routing.key";

    //Este valor es unico para el micro.
    public static final String SERVICE_TYPE_QUEUE = "service-type.queue.hospitalization";

    @Bean
    public TopicExchange serviceTypeExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // Cada microservicio debe usar un nombre de cola ÚNICO
    @Bean
    public Queue serviceTypeQueue() {
        return new Queue(SERVICE_TYPE_QUEUE, true);
    }

    @Bean
    public Binding ServiceTypeCreatedBinding() {
        return BindingBuilder.bind(serviceTypeQueue())
                .to(serviceTypeExchange())
                .with(SERVICE_TYPE_CREATED_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
