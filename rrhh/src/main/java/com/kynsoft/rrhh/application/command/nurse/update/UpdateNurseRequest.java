package com.kynsoft.rrhh.application.command.nurse.update;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateNurseRequest {
    private String identification;
    private String code;
    private String email;
    private String name;
    private String lastName;
    private String status;
    private String registerNumber;
    private String language;
    private boolean isExpress;
    private String clasificacion; // Campo añadido para almacenar la clasificación de la enfermera
    private String phoneNumber;
    private String image;
}