package com.kynsoft.invoiceservice.infrastructure.service.rabbitmq.consumer.impl;


import com.kynsoft.invoiceservice.application.query.customer.get.CustomerDto;
import com.kynsoft.invoiceservice.domain.service.ICustomerService;
import com.kynsoft.invoiceservice.infrastructure.entities.Customer;
import com.kynsoft.invoiceservice.infrastructure.repository.query.CustomerReadRepository;
import com.kynsoft.invoiceservice.infrastructure.service.rabbitmq.consumer.dto.RabbitMQPatientDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EventConsumerCustomerService {
    private final ICustomerService service;
    private final CustomerReadRepository query;

    public EventConsumerCustomerService(ICustomerService service, CustomerReadRepository query) {
        this.service = service;
        this.query = query;
    }
    @RabbitListener(queues = "patient_customer.queue.invoice")
    public void handleCompanyEvent(RabbitMQPatientDto event) {
        Optional<Customer> customer  = this.query.findByIdNumber(event.getIdentification());
        if(customer.isPresent()){
            CustomerDto customerDto = this.mapEntityToDto(customer.get());
            customerDto.setEmail(event.getEmail());
            customerDto.setAddress(event.getAddress());
            customerDto.setPhoneNumber(event.getTelephone());
            customerDto.setBusinessName(event.getName()+" "+event.getLastName());
            this.service.update(customerDto);
        }else{
            CustomerDto customerDto = CustomerDto.builder()
                    .id(UUID.randomUUID())
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
        }



    }
    private CustomerDto mapEntityToDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .identificationType(customer.getIdType())
                .identificationNumber(customer.getIdNumber())
                .businessName(customer.getBusinessName())
                .address(customer.getAddress())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhone())
                .isActive(customer.getIsActive())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .createdBy(customer.getCreatedBy())
                .updatedBy(customer.getUpdatedBy())
                .build();
    }
}
