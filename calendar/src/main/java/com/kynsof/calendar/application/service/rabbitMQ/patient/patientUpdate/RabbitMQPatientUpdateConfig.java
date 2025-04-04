package com.kynsof.calendar.application.service.rabbitMQ.patient.patientUpdate;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQPatientUpdateConfig {

    private static final String QUEUE_NAME = "paciente.update.calendar";
    private static final String EXCHANGE_NAME = "paciente.update.exchange";

    @Bean(name = "calendarUpdateQueue")
    public Queue calendarUpdateQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean(name = "calendarUpdateExchange")
    public FanoutExchange calendarUpdateExchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding bindCalendarUpdateQueue(
            @Qualifier("calendarUpdateQueue") Queue queue,
            @Qualifier("calendarUpdateExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }
}