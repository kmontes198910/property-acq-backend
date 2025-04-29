package com.kynsoft.cirugia.infrastructure.entities;

import com.kynsoft.cirugia.domain.dto.PatientDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "patients")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Patient {

    @Id
    private UUID id;
    private String identification;
    private String email;
    private String name;
    private String lastName;
    private String image;
    private String profession;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Patient(PatientDto dto) {
        this.id = dto.getId();
        this.identification = dto.getIdentification();
        this.email = dto.getEmail();
        this.name = dto.getName();
        this.lastName = dto.getLastName();
        this.image = dto.getImage();
        this.profession = dto.getProfession();
    }

    public PatientDto toAggregate() {
        return new PatientDto(id, identification, email, name, lastName, image, profession);
    }

}
