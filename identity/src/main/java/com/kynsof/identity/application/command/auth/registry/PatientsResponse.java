package com.kynsof.identity.application.command.auth.registry;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class PatientsResponse {
    private UUID id;
    private String identification;
    private String name;
    private String lastName;
    private String gender;
    private Boolean hasDisability;
    private Boolean isPregnant;
    private String image;
    private String email;
    private String phone;
}