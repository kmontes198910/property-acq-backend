package com.kynsof.patients.infrastructure.services.rabbitMQ.patientCreate;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQPatientCreatedConfig {

    private static final String EXCHANGE_NAME = "paciente.exchange";

    private static final String TREATMENT_QUEUE = "paciente.treatment";
    private static final String PAYMENT_QUEUE = "paciente.payment";
    private static final String EVALUATION_QUEUE = "paciente.evaluation";
    private static final String HOSPITALIZATION_QUEUE = "paciente.hospitalization";
    private static final String CALENDAR_QUEUE = "paciente.calendar";

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean public Queue treatmentQueue() { return new Queue(TREATMENT_QUEUE, true); }
    @Bean public Queue paymentQueue() { return new Queue(PAYMENT_QUEUE, true); }
    @Bean public Queue evaluationQueue() { return new Queue(EVALUATION_QUEUE, true); }
    @Bean public Queue hospitalIzationQueue() { return new Queue(HOSPITALIZATION_QUEUE, true); }
    @Bean public Queue calendarQueue() { return new Queue(CALENDAR_QUEUE, true); }

    @Bean public Binding bindTreatmentQueue(FanoutExchange exchange, Queue treatmentQueue) {
        return BindingBuilder.bind(treatmentQueue).to(exchange);
    }

    @Bean public Binding bindPaymentQueue(FanoutExchange exchange, Queue paymentQueue) {
        return BindingBuilder.bind(paymentQueue).to(exchange);
    }

    @Bean public Binding bindEvaluationQueue(FanoutExchange exchange, Queue evaluationQueue) {
        return BindingBuilder.bind(evaluationQueue).to(exchange);
    }

    @Bean public Binding bindHospitalIzationQueue(FanoutExchange exchange, Queue hospitalIzationQueue) {
        return BindingBuilder.bind(hospitalIzationQueue).to(exchange);
    }

    @Bean public Binding bindCalendarQueue(FanoutExchange exchange, Queue calendarQueue) {
        return BindingBuilder.bind(calendarQueue).to(exchange);
    }
}