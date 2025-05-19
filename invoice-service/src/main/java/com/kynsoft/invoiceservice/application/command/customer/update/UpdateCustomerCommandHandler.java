package com.kynsoft.invoiceservice.application.command.customer.update;

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
public class UpdateCustomerCommandHandler implements ICommandHandler<UpdateCustomerCommand> {

    private final ICustomerService customerService;

    @Override
    @Transactional
    public void handle(UpdateCustomerCommand command) {
        log.info("Updating customer with ID: {}", command.getId());
        
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
                .updatedBy(command.getUpdatedBy())
                .build();
        
        // Utilizar el servicio para actualizar el cliente
        customerService.update(customerDto);
    }
}