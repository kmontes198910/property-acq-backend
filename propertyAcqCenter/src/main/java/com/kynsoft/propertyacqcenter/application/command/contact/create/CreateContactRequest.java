package com.kynsoft.propertyacqcenter.application.command.contact.create;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateContactRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String position;
    private String department;
    private String category;
    private String company;
    private String notes;
    private Boolean isActive;
    private UUID legalEntity;
    private UUID business;
}
