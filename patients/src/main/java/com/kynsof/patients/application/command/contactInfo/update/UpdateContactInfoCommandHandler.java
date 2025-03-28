package com.kynsof.patients.application.command.contactInfo.update;

import com.kynsof.patients.domain.dto.ContactInfoDto;
import com.kynsof.patients.domain.dto.GeographicLocationDto;
import com.kynsof.patients.domain.dto.enumTye.Status;
import com.kynsof.patients.domain.service.IContactInfoService;
import com.kynsof.patients.domain.service.IGeographicLocationService;
import com.kynsof.patients.domain.service.IPatientsService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class UpdateContactInfoCommandHandler implements ICommandHandler<UpdateContactInfoCommand> {

    private final IPatientsService patientsService;
    private final IContactInfoService contactInfoService;
    private final IGeographicLocationService geographicLocationService;

    public UpdateContactInfoCommandHandler(IPatientsService patientsService, IContactInfoService contactInfoService,
                                           IGeographicLocationService geographicLocationService) {
        this.patientsService = patientsService;
        this.contactInfoService = contactInfoService;
        this.geographicLocationService = geographicLocationService;
    }

    @Override
    public void handle(UpdateContactInfoCommand command) {
        GeographicLocationDto parroquia = geographicLocationService.findById(command.getParroquia());

        ContactInfoDto contactInfoDto = contactInfoService.findById(command.getId());
        contactInfoDto.setAddress(command.getAddress());
        contactInfoDto.setBirthdayDate(command.getBirthdayDate());
        contactInfoDto.setTelephone(command.getTelephone());
        contactInfoDto.setStatus(Status.ACTIVE);
        contactInfoDto.setParroquia(parroquia);
        contactInfoDto.setConventionalTelephone(command.getConventionalTelephone());
        contactInfoDto.setMaritalStatus(command.getMaritalStatus());

        contactInfoService.update(contactInfoDto);
    }
}
