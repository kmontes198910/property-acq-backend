package com.kynsof.treatments.application.command.businessProcedure.create;


import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.dto.BusinessDto;
import com.kynsof.treatments.domain.dto.BusinessProcedureDto;
import com.kynsof.treatments.domain.dto.ProcedureDto;
import com.kynsof.treatments.domain.service.IBusinessProcedureService;
import com.kynsof.treatments.domain.service.IBusiness;
import com.kynsof.treatments.domain.service.IProcedureService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CreateBusinessProcedureCommandHandler implements ICommandHandler<CreateBusinessProcedureCommand> {

    private final IBusinessProcedureService businessProcedureService;
    private final IBusiness businessService;
    private final IProcedureService procedureService;

    public CreateBusinessProcedureCommandHandler(IBusinessProcedureService service, IBusiness serviceBusiness, IProcedureService procedureService) {
        this.businessProcedureService = service;
        this.businessService = serviceBusiness;
        this.procedureService = procedureService;
    }

    @Override
    public void handle(CreateBusinessProcedureCommand command) {
        BusinessDto _business = businessService.findById(command.getIdBusiness());

        List<BusinessProcedureDto> businessProcedureDtos = command.getProcedurePrices().stream().map(
                businessProcedurePriceRequest -> {
                    ProcedureDto procedureDto = this.procedureService.findById(businessProcedurePriceRequest.getProcedure());
                    return new BusinessProcedureDto(UUID.randomUUID(), _business, procedureDto, businessProcedurePriceRequest.getPrice(),
                            businessProcedurePriceRequest.getCode());

                }
        ).toList();
        this.businessProcedureService.create(businessProcedureDtos);
    }
}
