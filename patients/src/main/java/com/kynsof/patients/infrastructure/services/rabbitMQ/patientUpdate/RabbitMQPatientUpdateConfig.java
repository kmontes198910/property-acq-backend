package com.kynsof.patients.infrastructure.services.rabbitMQ.patientUpdate;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQPatientUpdateConfig {

    private static final String EXCHANGE_NAME = "paciente.update.exchange";

    private static final String TREATMENT_QUEUE = "paciente.update.treatment";
    private static final String PAYMENT_QUEUE = "paciente.update.payment";
    private static final String EVALUATION_QUEUE = "paciente.update.evaluation";
    private static final String HOSPITALIZATION_QUEUE = "paciente.update.hospitalization";
    private static final String CALENDAR_QUEUE = "paciente.update.calendar";

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