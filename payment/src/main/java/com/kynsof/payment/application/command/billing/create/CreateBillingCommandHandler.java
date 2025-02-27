package com.kynsof.payment.application.command.billing.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.payment.domain.dto.BillingDto;
import com.kynsof.payment.domain.dto.BusinessDto;
import com.kynsof.payment.domain.dto.ClientDto;
import com.kynsof.payment.domain.dto.enumDto.BillingStatus;
import com.kynsof.payment.domain.service.IBillingService;
import com.kynsof.payment.domain.service.IBusiness;
import com.kynsof.payment.domain.service.IClientService;
import org.springframework.stereotype.Component;

@Component
public class CreateBillingCommandHandler implements ICommandHandler<CreateBillingCommand> {

    private final IBillingService serviceImpl;
    private final IClientService clientService;
    private final IBusiness businessService;

    public CreateBillingCommandHandler(IBillingService serviceImpl, IClientService clientService, IBusiness businessService) {
        this.serviceImpl = serviceImpl;
        this.clientService = clientService;
        this.businessService = businessService;
    }

    @Override
    public void handle(CreateBillingCommand command) {
        if (!this.serviceImpl.existsByCodeAndBusinessIdAndStatusAndPatientId(command.getCode(), command.getBusinessId(), BillingStatus.PENDING, command.getClientId())) {
            ClientDto clientDto = clientService.findById(command.getClientId());
            BusinessDto businessDto = businessService.findById(command.getBusinessId());

            BillingDto create = new BillingDto(
                    command.getId(),
                    command.getClientId(),
                    command.getBusinessId(),
                    command.getCode(),
                    command.getDescription(),
                    command.getStatus(),
                    command.isProforma(),
                    command.getCost()
            );

            create.setClient(clientDto);
            create.setBusiness(businessDto);

            this.serviceImpl.create(create);
        } else {
            throw new BusinessNotFoundException(new GlobalBusinessException(
                    DomainErrorMessage.BILLING_SERVICE_NOT_FOUND,
                    new ErrorField("code", "Ya se encuentra registrado un pago con ese código para el paciente.")));
        }
    }

}
