package com.kynsoft.rrhh.infrastructure.identity;

import com.kynsoft.rrhh.domain.dto.DoctorDto;
import com.kynsoft.rrhh.domain.dto.NurseDto;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Nurse extends UserSystem {
    private String registerNumber;
    private String language;
    private boolean isExpress;
    private String clasificacion; // Para almacenar: "licenciada", "tecnica", u otras clasificaciones

    public Nurse() {
        super();
    }

    public Nurse(NurseDto dto) {
        super();
        this.setId(dto.getId() != null ? dto.getId() : UUID.randomUUID());
        this.setIdentification(dto.getIdentification());
        this.setCode(dto.getCode());
        this.setName(dto.getName());
        this.setLastName(dto.getLastName());
        this.setEmail(dto.getEmail());
        this.setStatus(dto.getStatus());
        this.setPhoneNumber(dto.getPhoneNumber());
        this.setImage(dto.getImage());
        this.setUpdatedAt(LocalDateTime.now());
        this.setCreatedAt(LocalDateTime.now());
        this.setRegisterNumber(dto.getRegisterNumber());
        this.setLanguage(dto.getLanguage());
        this.setExpress(dto.isExpress());
        this.setClasificacion(dto.getClasificacion());
    }

    public NurseDto toAggregate() {
        return new NurseDto(
                this.getId(), 
                this.getIdentification(), 
                this.getCode(), 
                this.getEmail(), 
                this.getName(), 
                this.getLastName(), 
                this.getStatus(), 
                this.getRegisterNumber(), 
                this.getLanguage(), 
                this.isExpress(),
                this.getClasificacion(),
                this.getPhoneNumber(), 
                this.getImage()
        );
    }
}