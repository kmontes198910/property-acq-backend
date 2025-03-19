//package com.kynsof.patients.infrastructure.services.rabbitMQ;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class RabbitMQConsumer {
//
//    @RabbitListener(queues = "${rabbitmq.queue.name}")
//    public void receiveMessage(String message) {
//        System.out.println("Mensaje recibido en la cola 'paciente': " + message);
//    }
//}