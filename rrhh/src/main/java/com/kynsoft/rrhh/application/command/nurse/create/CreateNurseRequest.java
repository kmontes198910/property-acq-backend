package com.kynsoft.rrhh.application.command.nurse.create;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateNurseRequest {
    private String identification;
    private String code;
    private String email;
    private String name;
    private String lastName;
    private String registerNumber;
    private String language;
    private boolean isExpress;
    private String clasificacion; // Para almacenar: "licenciada", "tecnica", u otras clasificaciones
    private String phoneNumber;
    private String image;
    private UUID business;
}