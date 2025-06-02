package com.kynsoft.medicaltest.application.command.labTestRequest.create;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LabTestItemRequestRequest {

    private String code;
    private String examinationType;
    private String status;
    private String observations;    
}
