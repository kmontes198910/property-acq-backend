package com.kynsof.identity.infrastructure.services.rabbitMq;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQBusinessConfig {

    @Value("${rabbitmq.exchange.business}")
    public String BUSINESS_EXCHANGE;
    @Value("${rabbitmq.routing.key.business}")
    public String BUSINESS_CREATED_ROUTING_KEY;

    //Business
    @Bean
    public TopicExchange businessExchange() {
        return new TopicExchange(BUSINESS_EXCHANGE);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
