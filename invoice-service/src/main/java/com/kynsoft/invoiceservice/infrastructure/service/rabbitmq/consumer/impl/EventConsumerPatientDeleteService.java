package com.kynsoft.invoiceservice.infrastructure.service.rabbitmq.consumer.impl;

import com.kynsoft.invoiceservice.application.query.customer.get.CustomerDto;
import com.kynsoft.invoiceservice.domain.exception.BusinessInvoiceException;
import com.kynsoft.invoiceservice.domain.service.ICustomerService;
import com.kynsoft.invoiceservice.infrastructure.entities.Customer;
import com.kynsoft.invoiceservice.infrastructure.repository.query.CustomerReadRepository;
import com.kynsoft.invoiceservice.infrastructure.service.rabbitmq.consumer.dto.RabbitMQPatientDeleteDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class EventConsumerPatientDeleteService {

    private final ICustomerService service;

    public EventConsumerPatientDeleteService(ICustomerService service) {
        this.service = service;
    }

    @RabbitListener(queues = "patient.delete.queue.invoice")
    public void handleCompanyEvent(RabbitMQPatientDeleteDto event) {
        try {
            CustomerDto customerDto = service.findByIdentificationNumber(event.getIdentification());
            service.deleteSystem(customerDto);
        } catch (BusinessInvoiceException ex) {
            if (ex.getMessage().contains("Cliente no encontrado")) {
                log.warn("No se pudo eliminar el customer porque no existe: {}", event.getIdentification());
            } else {
                throw ex;
            }
        }
    }
}