package com.kynsof.hospitalizationService.application.response;

import com.kynsof.hospitalizationService.domain.dto.*;
import com.kynsof.share.core.domain.bus.query.IResponse;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponsibleMedicalStaffResponse implements IResponse {
    private UUID id;
    private EmergencyCaseDto emergencyCase;
    private String firstName;
    private String lastName;
    private String identificationNumber;
    private LocalDate dateOfRecord;
    private String signature;
    private String seal;

    public ResponsibleMedicalStaffResponse(ResponsibleMedicalStaffDto dto) {
        this.id = dto.getId();
        this.emergencyCase = dto.getEmergencyCase();
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.identificationNumber = dto.getIdentificationNumber();
        this.dateOfRecord = dto.getDateOfRecord();
        this.signature = dto.getSignature();
        this.seal = dto.getSeal();
    }

}
