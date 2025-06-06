package com.kynsoft.settings.infrastructure.services.rabbitMq;


import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQRecoveryConfig {
    public static final String EXCHANGE_NAME_BED = "recovery-bed.exchange";
    public static final String EXCHANGE_NAME_ROOM = "recovery-room.exchange";
    public static final String RECOVERY_CREATED_ROUTING_KEY = "recovery.routing.key";

    @Bean
    public TopicExchange recoveryExchangeBed() {
        return new TopicExchange(EXCHANGE_NAME_BED);
    }
    @Bean
    public TopicExchange recoveryExchangeRoom() {
        return new TopicExchange(EXCHANGE_NAME_ROOM);
    }
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
