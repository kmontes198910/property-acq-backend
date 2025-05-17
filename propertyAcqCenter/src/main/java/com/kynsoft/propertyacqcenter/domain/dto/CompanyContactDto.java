package com.kynsoft.propertyacqcenter.domain.dto;

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
    private String department;
    private String category;
    private String notes;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}