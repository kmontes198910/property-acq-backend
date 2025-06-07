package com.kynsof.hospitalizationService.infrastructure.service.rabbitMQ.consumer;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConsumerRecoveryRoomConfig {
    //Este valor debe de ser igual al micro que produce
    public static final String EXCHANGE_NAME = "recovery-room.exchange";

    //Este valor debe de ser igual al micro que produce
    public static final String RECOVERY_CREATED_ROUTING_KEY = "recovery.routing.key";

    //Este valor es unico para el micro.
    public static final String RECOVERY_ROOM_QUEUE = "recovery-room.queue.hospitalization";

    @Bean
    public TopicExchange recoveryRoomExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // Cada microservicio debe usar un nombre de cola ÚNICO
    @Bean
    public Queue recoveryRoomQueue() {
        return new Queue(RECOVERY_ROOM_QUEUE, true);
    }

    @Bean
    public Binding RecoveryRoomCreatedBinding() {
        return BindingBuilder.bind(recoveryRoomQueue())
                .to(recoveryRoomExchange())
                .with(RECOVERY_CREATED_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
