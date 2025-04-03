package com.kynsof.calendar.application.service.rabbitMQ.patient.patientCreate;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQPatientCreateConfig {

    private static final String QUEUE_NAME = "paciente.calendar";
    private static final String EXCHANGE_NAME = "paciente.exchange";

    @Bean(name = "calendarCreateQueue")
    public Queue calendarCreateQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean(name = "calendarCreateExchange")
    public FanoutExchange calendarCreateExchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding bindCalendarCreateQueue(
            @Qualifier("calendarCreateQueue") Queue queue,
            @Qualifier("calendarCreateExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }
}