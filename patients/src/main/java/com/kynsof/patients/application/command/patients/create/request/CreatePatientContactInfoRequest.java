package com.kynsof.patients.application.command.patients.create.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePatientContactInfoRequest {
    private String email;
    private String telephone;
    private String address;
    private LocalDate birthdayDate;
    private UUID parroquia;
    private String conventionalTelephone;
    private String maritalStatus;
}
