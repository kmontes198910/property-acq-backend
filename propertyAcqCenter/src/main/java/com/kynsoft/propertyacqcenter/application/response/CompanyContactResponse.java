package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
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
    private CompanyDto company;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String personalEmail;
    private String position;
    private String department;
    private String category;
    private String notes;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private SubCategoryDto subCategory;

    public CompanyContactResponse(CompanyContactDto dto) {
        this.id = dto.getId();
        this.company = dto.getCompany();
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