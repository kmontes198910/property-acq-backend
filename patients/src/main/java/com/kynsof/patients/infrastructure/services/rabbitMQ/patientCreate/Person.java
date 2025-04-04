package com.kynsof.patients.infrastructure.services.rabbitMQ.patientCreate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private String id;
    private String identificationNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String image;
    private LocalDate birthDate;
    private String gender;
    private String phoneNumber;
    private String profession;
}