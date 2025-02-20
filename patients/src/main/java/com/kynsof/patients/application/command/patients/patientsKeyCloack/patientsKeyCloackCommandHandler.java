package com.kynsof.patients.application.command.patients.patientsKeyCloack;

import com.kynsof.patients.domain.dto.ContactInfoDto;
import com.kynsof.patients.domain.dto.GeographicLocationDto;
import com.kynsof.patients.domain.dto.PatientDto;
import com.kynsof.patients.domain.dto.enumTye.Status;
import com.kynsof.patients.domain.service.IContactInfoService;
import com.kynsof.patients.domain.service.IGeographicLocationService;
import com.kynsof.patients.domain.service.IPatientsService;
import com.kynsof.patients.infrastructure.services.kafka.producer.customer.ProducerCreateCustomerEventService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.kafka.entity.CustomerKafka;
import org.springframework.stereotype.Component;

@Component
public class patientsKeyCloackCommandHandler implements ICommandHandler<patientsKeyCloakCommand> {

    private final IPatientsService serviceImpl;


    public patientsKeyCloackCommandHandler(IPatientsService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(patientsKeyCloakCommand command) {
        serviceImpl.updateKeyCloak(command.getId(), command.getKeyCloackId());

    }
}
