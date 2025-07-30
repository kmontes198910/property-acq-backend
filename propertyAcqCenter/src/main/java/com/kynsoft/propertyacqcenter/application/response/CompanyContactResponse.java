package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
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
public class CompanyContactResponse implements IResponse {

    private UUID id;
    private LocalDate birthDate;
    private CompanyDto company;
    private LegalEntityBasicResponse legalEntity;
    private SubCategoryResponse subCategory;
    private ContactType category;
    private Type type;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String personalEmail;
    private String position;
    private DepartmentType department;
    private String notes;
    private Boolean isActive;
    private String mailingAddress;
    private Boolean isEmployee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CompanyContactResponse(CompanyContactDto dto) {
        this.id = dto.getId();
        this.birthDate = dto.getBirthDate();
        this.company = dto.getCompany();
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.email = dto.getEmail();
        this.phoneNumber = dto.getPhoneNumber();
        this.position = dto.getPosition();
        this.department = dto.getDepartment();
        this.notes = dto.getNotes();
        this.isActive = dto.getIsActive();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.personalEmail = dto.getPersonalEmail();
        this.mailingAddress = dto.getMailingAddress();
        this.isEmployee = dto.getIsEmployee();
        this.legalEntity = dto.getLegalEntity() != null ? new LegalEntityBasicResponse(dto.getLegalEntity()) : null;
        this.subCategory = dto.getSubCategory() != null ? new SubCategoryResponse(dto.getSubCategory()) : null;
        this.category = dto.getCategory();
        this.type = dto.getType();
    }

}