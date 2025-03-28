package com.kynsof.patients.application.command.patients.update;

import com.kynsof.patients.application.command.patients.create.request.CreatePatientContactInfoRequest;
import com.kynsof.patients.domain.dto.enumTye.GenderType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePatientsRequest {

    private String identification;
    private String name;
    private String lastName;
    private GenderType gender;
    private String image;
    private CreatePatientContactInfoRequest contactInfo;
    private String profession;
    private String educationalLevel;
    private int clinicalHistoryNumber;
}
