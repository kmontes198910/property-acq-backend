package com.kynsof.evaluation.application.command.patients.update;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdatePatientsRequest {

    private String identification;
    private String name;
    private String lastName;
    private String gender;
    private LocalDate birthDate;
}
