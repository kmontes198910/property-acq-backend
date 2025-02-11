package com.kynsof.patients.domain.dto;

import com.kynsof.patients.domain.dto.enumTye.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter

@NoArgsConstructor
public class ContactInfoDto implements Serializable {
    private  UUID id;
    private  PatientDto patient;
    private  String email;
    private  String telephone;
    private  String address;
    private  LocalDate birthdayDate;
    private  Status status;
    private  GeographicLocationDto parroquia;

    public ContactInfoDto(UUID id, PatientDto patient, String email, String telephone, String address,
                          LocalDate birthdayDate, Status status, GeographicLocationDto parroquia) {
        this.id = id;
        this.patient = patient;
        this.email = email != null ? email : "";
        this.telephone = telephone;
        this.address = address;
        this.birthdayDate = birthdayDate;
        this.status = status;
        this.parroquia = parroquia;
    }
}
