package com.kynsoft.rrhh.infrastructure.services.rabbitMQ;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQAssistantConfig {

    public static final String ASSISTANT_EXCHANGE = "assistant.topic.exchange";
    public static final String ASSISTANT_CREATED_ROUTING_KEY = "assistant.routing.key";

    //Assistant
    @Bean
    public TopicExchange assistantExchange() {
        return new TopicExchange(ASSISTANT_EXCHANGE);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
