package com.kynsof.payment.application.command.billing.createall;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.payment.domain.dto.BillingDto;
import com.kynsof.payment.domain.dto.BusinessDto;
import com.kynsof.payment.domain.dto.ClientDto;
import com.kynsof.payment.domain.service.IBillingService;
import com.kynsof.payment.domain.service.IBusiness;
import com.kynsof.payment.domain.service.IClientService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CreateAllBillingCommandHandler implements ICommandHandler<CreateAllBillingCommand> {

    private final IBillingService serviceImpl;
    private final IClientService clientService ;
    private final IBusiness businessService;

    public CreateAllBillingCommandHandler(IBillingService serviceImpl, IClientService clientService, IBusiness businessService) {

        this.serviceImpl = serviceImpl;
        this.clientService = clientService;
        this.businessService = businessService;
    }

    @Override
    public void handle(CreateAllBillingCommand command) {

        ClientDto clientDto = clientService.findById(command.getClientId());
        BusinessDto businessDto = businessService.findById(command.getBusinessId());
        List<BillingDto> billingDtoList = new ArrayList<>();
        command.getBillingPartialRequests().forEach(createBillingPartialRequest -> {
            BillingDto create = new BillingDto(
                    UUID.randomUUID(),
                    command.getClientId(),
                    command.getBusinessId(),
                    createBillingPartialRequest.getCode(),
                    createBillingPartialRequest.getDescription(),
                    createBillingPartialRequest.getStatus(),
                    command.isProforma(),
                    createBillingPartialRequest.getCost()
            );

            create.setClient(clientDto);
            create.setBusiness(businessDto);
            billingDtoList.add(create);
        });
        this.serviceImpl.createAll(billingDtoList);
        command.setResult(true);

    }
}
