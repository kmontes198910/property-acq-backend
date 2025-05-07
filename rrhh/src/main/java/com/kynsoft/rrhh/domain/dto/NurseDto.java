package com.kynsoft.rrhh.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class NurseDto extends UserSystemDto implements Serializable {
    private String registerNumber;
    private String language;
    private boolean isExpress;
    private String clasificacion; // Para almacenar: "licenciada", "tecnica", u otras clasificaciones

    public NurseDto(UUID id, String identification, String code, String email, String name, String lastName, String status,
                   String registerNumber, String language, boolean isExpress, String clasificacion, String phoneNumber, String image) {
        super(id, identification, code, name, lastName, email, status, phoneNumber, image);
        this.registerNumber = registerNumber;
        this.language = language;
        this.isExpress = isExpress;
        this.clasificacion = clasificacion;
    }
}