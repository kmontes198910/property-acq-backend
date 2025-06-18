//package com.kynsoft.invoiceservice.infrastructure.service.rabbitmq.consumer;
//
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitMQConsumerCustomerConfig {
//    //Este valor debe de ser igual al micro que produce
//    public static final String CUSTOMER_EXCHANGE = "patient.topic.exchange";
//
//    //Este valor debe de ser igual al micro que produce
//    public static final String CUSTOMER_CREATED_ROUTING_KEY = "patient.routing.key";
//
//    //Este valor es unico para el micro.
//    public static final String CUSTOMER_QUEUE = "patient_customer.queue.invoice";
//
//    @Bean
//    public TopicExchange customerExchange() {
//        return new TopicExchange(CUSTOMER_EXCHANGE);
//    }
//
//    // Cada microservicio debe usar un nombre de cola ÚNICO
//    @Bean
//    public Queue customerCreatedQueue() {
//        return new Queue(CUSTOMER_QUEUE, true);
//    }
//
//    @Bean
//    public Binding customerCreatedBinding() {
//        return BindingBuilder.bind(customerCreatedQueue())
//                .to(customerExchange())
//                .with(CUSTOMER_CREATED_ROUTING_KEY);
//    }
//
//    @Bean
//    public Jackson2JsonMessageConverter jsonMessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
//}
