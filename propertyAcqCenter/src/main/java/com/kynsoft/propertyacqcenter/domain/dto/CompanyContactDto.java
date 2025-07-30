package com.kynsoft.propertyacqcenter.domain.dto;

import com.kynsoft.propertyacqcenter.domain.enums.ContactType;
import com.kynsoft.propertyacqcenter.domain.enums.DepartmentType;
import com.kynsoft.propertyacqcenter.domain.enums.Type;
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
    private String mailingAddress;
    private Boolean isEmployee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private LegalEntityDto legalEntity;
    private SubCategoryDto subCategory;
    private ContactType category;
    private Type type;
}