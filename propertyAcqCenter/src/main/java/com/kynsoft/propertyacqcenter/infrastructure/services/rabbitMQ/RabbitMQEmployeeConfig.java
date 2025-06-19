package com.kynsoft.propertyacqcenter.infrastructure.services.rabbitMQ;


import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQEmployeeConfig {
    public static final String EXCHANGE_NAME_EMPLOYEE = "employee.exchange";
    public static final String EMPLOYEE_CREATED_ROUTING_KEY = "employee.routing.key";

    @Bean
    public TopicExchange employeeExchangeBed() {
        return new TopicExchange(EXCHANGE_NAME_EMPLOYEE);
    }
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

