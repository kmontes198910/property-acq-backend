package com.kynsof.patients.infrastructure.entity;

import com.kynsof.patients.domain.dto.ContactInfoDto;
import com.kynsof.patients.domain.dto.GeographicLocationDto;
import com.kynsof.patients.domain.dto.enumTye.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class ContactInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "patients_id", referencedColumnName = "id")
    private Patients patient;

    private String email;

    private String telephone;

    private String address;

    private LocalDate birthdayDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne()
    @JoinColumn(name = "parroquia_id")
    private GeographicLocation parroquia;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String conventionalTelephone;
    private String maritalStatus;

    public ContactInformation(ContactInfoDto contactInfoDto) {
        this.id = contactInfoDto.getId();
        this.patient = new Patients(contactInfoDto.getPatient());
        this.email = contactInfoDto.getEmail()!= null ? contactInfoDto.getEmail() : null;
        this.telephone = contactInfoDto.getTelephone() != null? contactInfoDto.getTelephone() : null;
        this.address = contactInfoDto.getAddress();
        this.birthdayDate = contactInfoDto.getBirthdayDate();
        this.status = contactInfoDto.getStatus();
        this.parroquia = contactInfoDto.getParroquia() != null ? new GeographicLocation(contactInfoDto.getParroquia()) : null;
        this.conventionalTelephone = contactInfoDto.getConventionalTelephone();
        this.maritalStatus = contactInfoDto.getMaritalStatus();
    }

    public ContactInfoDto toAggregate() {

        GeographicLocationDto parroquiaDto = parroquia != null ? parroquia.toAggregate() : null;
        return new ContactInfoDto(
                getId(), 
                getPatient().toAggregate(), 
                getEmail(), 
                getTelephone(), 
                getAddress(), 
                getBirthdayDate(), 
                getStatus(),
                parroquiaDto,
                getConventionalTelephone(),
                getMaritalStatus()
        );
    }

    public ContactInfoDto toAggregateSimple() {

        GeographicLocationDto parroquiaDto = parroquia != null ? parroquia.toAggregate() : null;
        return new ContactInfoDto(
                getId(),
                null,
                getEmail() != null ? getEmail() : "",
                getTelephone() != null ? getTelephone() : "",
                getAddress(),
                getBirthdayDate(),
                getStatus(),
                parroquiaDto,
                getConventionalTelephone(),
                getMaritalStatus()
        );
    }
}
