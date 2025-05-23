package com.kynsoft.medicaltest.infrastructure.entities;

import com.kynsoft.medicaltest.domain.dto.DoctorDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Doctor {
    @Id
    @Column(name="id")
    private UUID id;
    private String identification;
    private String name;
    private String lastName;
    private String registerNumber;
    private String image;

    public Doctor(DoctorDto doctor) {
        this.id = doctor.getId();
        this.name = doctor.getName();
        this.image = doctor.getImage();
        this.lastName = doctor.getLastName();
        this.registerNumber = doctor.getRegisterNumber();
        this.identification = doctor.getIdentification();

    }

    public DoctorDto toAggregate() {
        return new DoctorDto(this.id,this.identification,
                this.name, this.lastName,this.registerNumber,
                this.image);
    }
}
