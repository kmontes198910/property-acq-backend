package com.kynsof.treatments.application.command.businessProcedure.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.dto.BusinessProcedureDto;
import com.kynsof.treatments.domain.dto.InsuranceDto;
import com.kynsof.treatments.domain.service.IBusinessProcedureService;
import com.kynsof.treatments.domain.service.IInsuranceService;
import org.springframework.stereotype.Component;

@Component
public class UpdateBusinessProcedureCommandHandler implements ICommandHandler<UpdateBusinessProcedureCommand> {

    private final IBusinessProcedureService businessProcedureService;
    private final IInsuranceService insuranceService;


    public UpdateBusinessProcedureCommandHandler(IBusinessProcedureService businessProcedureService, IInsuranceService insuranceService) {

        this.businessProcedureService = businessProcedureService;
        this.insuranceService = insuranceService;
    }

    @Override
    public void handle(UpdateBusinessProcedureCommand command) {
        InsuranceDto insuranceDto = insuranceService.findById(command.getInsuranceId());
        BusinessProcedureDto businessProcedureDto = businessProcedureService.findById(command.getBusinessProcedureId());
        businessProcedureDto.setCode(command.getCode());
        businessProcedureDto.setPrice(command.getPrice());
        businessProcedureDto.setInsurance(insuranceDto);
        businessProcedureService.update(businessProcedureDto);
    }
}
