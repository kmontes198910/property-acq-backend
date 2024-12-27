package com.kynsof.patients.application.query.patientsInsurance.getById;


import com.kynsof.patients.application.query.contactInfo.getall.ContactInfoResponse;
import com.kynsof.patients.application.query.insuarance.getall.InsuranceResponse;
import com.kynsof.patients.application.query.patients.getall.PatientsResponse;
import com.kynsof.patients.domain.dto.PatientByIdDto;
import com.kynsof.patients.domain.dto.PatientInsuranceDto;
import com.kynsof.patients.domain.dto.enumTye.DisabilityType;
import com.kynsof.patients.domain.dto.enumTye.FamilyRelationship;
import com.kynsof.patients.domain.dto.enumTye.GenderType;
import com.kynsof.patients.domain.dto.enumTye.Status;
import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class PatientsInsuranceByIdResponse implements IResponse {
    private UUID id;
    private PatientsResponse patients;
    private InsuranceResponse insurance;
    private Status status;
    private LocalDateTime created;
    private LocalDateTime updateAt;

    public PatientsInsuranceByIdResponse(PatientInsuranceDto dto) {
        this.id = dto.getId();
        this.patients = new PatientsResponse(dto.getPatient());
        this.insurance = new InsuranceResponse(dto.getInsurance());
        this.status = dto.getStatus();
        this.created = dto.getCreated();
        this.updateAt = dto.getUpdateAt();
    }

}