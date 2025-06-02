package com.kynsoft.invoiceservice.application.command.customer.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.invoiceservice.application.query.customer.get.CustomerDto;
import com.kynsoft.invoiceservice.domain.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateCustomerCommandHandler implements ICommandHandler<CreateCustomerCommand> {

    private final ICustomerService customerService;

    @Override
    @Transactional
    public void handle(CreateCustomerCommand command) {
        log.info("Creating new customer with identification number: {}", command.getIdentificationNumber());
        
        // Mapear el comando a un DTO de cliente
        CustomerDto customerDto = CustomerDto.builder()
                .id(command.getId())
                .identificationType(command.getIdentificationType())
                .identificationNumber(command.getIdentificationNumber())
                .businessName(command.getBusinessName())
                .address(command.getAddress())
                .email(command.getEmail())
                .phoneNumber(command.getPhoneNumber())
                .isActive(command.getIsActive())
                .createdBy(command.getCreatedBy())
                .build();
        
        // Utilizar el servicio para crear el cliente
        customerService.create(customerDto);
    }
}