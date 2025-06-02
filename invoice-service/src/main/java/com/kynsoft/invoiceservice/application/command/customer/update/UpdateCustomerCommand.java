package com.kynsoft.invoiceservice.application.command.customer.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.invoiceservice.infrastructure.entities.IdentificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerCommand implements ICommand {
    private UUID id;
    private IdentificationType identificationType;
    private String identificationNumber;
    private String businessName;
    private String address;
    private String email;
    private String phoneNumber;
    private Boolean isActive;
    private UUID updatedBy;
    
    public static UpdateCustomerCommand fromRequest(UpdateCustomerRequest request, UUID updatedBy) {
        return new UpdateCustomerCommand(
                request.getId(),
                request.getIdentificationType(),
                request.getIdentificationNumber(),
                request.getBusinessName(),
                request.getAddress(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getIsActive(),
                updatedBy
        );
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new UpdateCustomerMessage(id);
    }
}