package com.kynsoft.invoiceservice.infrastructure.service.rabbitmq.consumer.impl;


import com.kynsoft.invoiceservice.application.query.customer.get.CustomerDto;
import com.kynsoft.invoiceservice.domain.exception.BusinessInvoiceException;
import com.kynsoft.invoiceservice.domain.service.ICustomerService;

import com.kynsoft.invoiceservice.infrastructure.service.rabbitmq.consumer.dto.RabbitMQPatientDto;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
@Slf4j
public class EventConsumerCustomerService {
    private final ICustomerService service;


    public EventConsumerCustomerService(ICustomerService service) {
        this.service = service;

    }
    @RabbitListener(queues = "patient_customer.queue.invoice")
    public void handleCompanyEvent(RabbitMQPatientDto event) {
        try {
            // Buscar cliente por número de identificación
            CustomerDto customerDto = service.findByIdentificationNumber(event.getIdentification());

            // Si existe, actualizar sus datos
            customerDto.setEmail(event.getEmail());
            customerDto.setAddress(event.getAddress());
            customerDto.setPhoneNumber(event.getTelephone());
            customerDto.setBusinessName(event.getName() + " " + event.getLastName());

            service.update(customerDto);
        } catch (BusinessInvoiceException ex) {
            // Si el cliente no existe, crear uno nuevo
            if (ex.getMessage().contains("Cliente no encontrado")) {
                CustomerDto newCustomerDto = CustomerDto.builder()
                        .id(UUID.randomUUID())
                        .identificationType(event.getIdentificationType())
                        .identificationNumber(event.getIdentification())
                        .businessName(event.getName() + " " + event.getLastName())
                        .address(event.getAddress())
                        .email(event.getEmail())
                        .phoneNumber(event.getTelephone())
                        .isActive(true)
                        .createdBy(null)
                        .build();

                service.create(newCustomerDto);
            } else {
                throw ex; // relanzar otros errores
            }
        }
    }
}