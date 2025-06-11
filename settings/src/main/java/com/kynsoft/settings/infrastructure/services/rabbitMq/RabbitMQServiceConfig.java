package com.kynsoft.settings.infrastructure.services.rabbitMq;


import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQServiceConfig {
    public static final String EXCHANGE_NAME_SERVICE = "service.exchange";
    public static final String EXCHANGE_NAME_SERVICE_TYPE = "service-type.exchange";
    public static final String SERVICE_CREATED_ROUTING_KEY = "service.routing.key";
    @Bean
    public TopicExchange serviceExchange() {
        return new TopicExchange(EXCHANGE_NAME_SERVICE);
    }
    @Bean
    public TopicExchange serviceTypeExchange() {
        return new TopicExchange(EXCHANGE_NAME_SERVICE_TYPE);
    }
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
