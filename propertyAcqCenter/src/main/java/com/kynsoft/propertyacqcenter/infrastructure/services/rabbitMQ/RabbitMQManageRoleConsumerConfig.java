package com.kynsoft.propertyacqcenter.infrastructure.services.rabbitMQ;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQManageRoleConsumerConfig {
    //Este valor debe de ser igual al micro que produce
    public static final String MANAGE_ROLE_EXCHANGE = "manage.role.topic.exchange";

    //Este valor debe de ser igual al micro que produce
    public static final String MANAGE_ROLE_CREATED_ROUTING_KEY = "manage.role.routing.key";

    //Este valor es unico para el micro.
    public static final String MANAGE_ROLE_QUEUE = "manage.role.queue.property.acq.center";

    @Bean
    public TopicExchange manageRoleExchange() {
        return new TopicExchange(MANAGE_ROLE_EXCHANGE);
    }

    // Cada microservicio debe usar un nombre de cola ÚNICO
    @Bean
    public Queue manageRoleCreatedQueue() {
        return new Queue(MANAGE_ROLE_QUEUE, true);
    }

    @Bean
    public Binding manageRoleCreatedBinding() {
        return BindingBuilder.bind(manageRoleCreatedQueue())
                .to(manageRoleExchange())
                .with(MANAGE_ROLE_CREATED_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
