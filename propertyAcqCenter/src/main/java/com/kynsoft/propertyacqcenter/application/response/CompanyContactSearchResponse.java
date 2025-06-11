package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
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
public class CompanyContactSearchResponse implements IResponse {

    private UUID id;
    private CompanyBasicResponse company;
    private LocalDate birthDate;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String personalEmail;
    private String position;
    private DepartmentType department;
    private String category;
    private String notes;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private SubCategoryDto subCategory;

    public CompanyContactSearchResponse(CompanyContactDto dto) {
        this.id = dto.getId();
        this.birthDate = dto.getBirthDate();
        this.company = new CompanyBasicResponse(dto.getCompany());
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.email = dto.getEmail();
        this.phoneNumber = dto.getPhoneNumber();
        this.position = dto.getPosition();
        this.department = dto.getDepartment();
        this.category = dto.getCategory();
        this.notes = dto.getNotes();
        this.isActive = dto.getIsActive();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.personalEmail = dto.getPersonalEmail();
        this.subCategory = dto.getSubCategory();
    }

}