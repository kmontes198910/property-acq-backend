package com.kynsoft.invoiceservice.infrastructure.service.rabbitmq.consumer.impl;


import com.kynsoft.invoiceservice.application.query.customer.get.CustomerDto;
import com.kynsoft.invoiceservice.domain.service.ICustomerService;
import com.kynsoft.invoiceservice.infrastructure.entities.Customer;
import com.kynsoft.invoiceservice.infrastructure.repository.query.CustomerReadRepository;
import com.kynsoft.invoiceservice.infrastructure.service.rabbitmq.consumer.dto.RabbitMQPatientDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
        log.error("Received event: {}", event);
        try {
            CustomerDto customer  = this.service.findByIdentificationNumber(event.getIdentification());
                customer.setEmail(event.getEmail());
                customer.setAddress(event.getAddress());
                customer.setPhoneNumber(event.getTelephone());
                customer.setBusinessName(event.getName()+" "+event.getLastName());
                this.service.update(customer);
        }catch (Exception e){
            log.error("Error processing event: {}", e.getMessage());
            log.error("Customer with identification {} not found, creating new customer", event.getIdentification());
            CustomerDto customerDto = CustomerDto.builder()
                    .id(event.getId() != null ? event.getId() : UUID.randomUUID())
                    .identificationType(event.getIdentificationType())
                    .identificationNumber(event.getIdentification())
                    .businessName(event.getName()+" "+event.getLastName())
                    .address(event.getAddress())
                    .email(event.getEmail())
                    .phoneNumber(event.getTelephone())
                    .isActive(true)
                    .createdBy(null)
                    .build();
            this.service.create(customerDto);
            log.info("Customer created successfully: {}", customerDto);
        }
    }
}
