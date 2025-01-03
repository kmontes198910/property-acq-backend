package com.kynsof.treatments.application.command.billing.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.treatments.domain.dto.BillingDto;
import com.kynsof.treatments.domain.dto.BusinessDto;
import com.kynsof.treatments.domain.dto.PatientDto;
import com.kynsof.treatments.domain.dto.enumDto.BillingStatus;
import com.kynsof.treatments.domain.service.IBillingService;
import com.kynsof.treatments.domain.service.IBusiness;
import com.kynsof.treatments.domain.service.IPatientsService;
import org.springframework.stereotype.Component;

@Component
public class CreateBillingCommandHandler implements ICommandHandler<CreateBillingCommand> {

    private final IBillingService serviceImpl;
    private final IPatientsService patientsService;
    private final IBusiness businessService;

    public CreateBillingCommandHandler(IBillingService serviceImpl, IPatientsService patientsService, IBusiness businessService) {
        this.serviceImpl = serviceImpl;
        this.patientsService = patientsService;
        this.businessService = businessService;
    }

    @Override
    public void handle(CreateBillingCommand command) {
        if (!this.serviceImpl.existsByCodeAndBusinessIdAndStatusAndPatientId(command.getCode(), command.getBusinessId(), BillingStatus.PENDING,
                command.getPatientId())) {
            PatientDto patientDto = patientsService.findById(command.getPatientId());
            BusinessDto businessDto = businessService.findById(command.getBusinessId());

            BillingDto create = new BillingDto(
                    command.getId(),
                    command.getPatientId(),
                    command.getBusinessId(),
                    command.getCode(),
                    command.getDescription(),
                    command.getStatus(),
                    command.isProforma(),
                    command.getCost()
            );

            create.setPatient(patientDto);
            create.setBusiness(businessDto);
            this.serviceImpl.create(create);
        }else{
            throw new BusinessNotFoundException(new GlobalBusinessException(
                    DomainErrorMessage.BILLING_SERVICE_NOT_FOUND,
                    new ErrorField("code", "Ya se encuentra registrado un pago con ese código para el paciente.")));
        }
    }

}
