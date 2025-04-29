package com.kynsoft.cirugia.infrastructure.entities;

import com.kynsoft.cirugia.domain.dto.DoctorDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "doctors")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Doctor {

    @Id
    private UUID id;
    private String name;
    private String lastName;
    private String identification;
    private String registerNumber;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Doctor(DoctorDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.lastName = dto.getLastName();
        this.identification = dto.getIdentification();
        this.registerNumber = dto.getRegisterNumber();
    }

    public DoctorDto toAggregate() {
        return new DoctorDto(id, name, lastName, identification, registerNumber);
    }

}
