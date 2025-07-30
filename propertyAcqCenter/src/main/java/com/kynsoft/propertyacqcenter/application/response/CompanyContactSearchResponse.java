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
public class CompanyContactSearchResponse implements IResponse {

    private UUID id;
    private CompanyBasicResponse company;
    private LegalEntityBasicResponse legalEntity;
    private SubCategoryResponse subCategory;
    private ContactType category;
    private Type type;
    private LocalDate birthDate;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CompanyContactSearchResponse(CompanyContactDto dto) {
        this.id = dto.getId();
        this.birthDate = dto.getBirthDate();
        this.company = dto.getCompany() != null ? new CompanyBasicResponse(dto.getCompany()) : null;
        this.legalEntity = dto.getLegalEntity() != null ? new LegalEntityBasicResponse(dto.getLegalEntity()) : null;
        this.subCategory = dto.getSubCategory() != null ? new SubCategoryResponse(dto.getSubCategory()) : null;
        this.category = dto.getCategory() != null ? dto.getCategory() : null;
        this.type = dto.getType() != null ? dto.getType() : null;
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
    }

}