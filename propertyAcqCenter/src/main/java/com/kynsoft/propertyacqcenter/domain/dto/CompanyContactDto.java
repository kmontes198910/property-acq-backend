package com.kynsoft.propertyacqcenter.domain.dto;

import com.kynsoft.propertyacqcenter.domain.enums.DepartmentType;
import java.time.LocalDate;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyContactDto {

    private UUID id;
    private CompanyDto company;
    private String firstName;
    private String lastName;
    private String email;
    private String personalEmail;
    private String phoneNumber;
    private String position;
    private DepartmentType department;
    private String notes;
    private Boolean isActive;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}