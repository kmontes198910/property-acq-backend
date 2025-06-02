package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.ContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryDto;
import com.kynsoft.propertyacqcenter.domain.enums.ContactType;
import com.kynsoft.propertyacqcenter.domain.enums.DepartmentType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactResponse implements IResponse {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String position;
    private DepartmentType department;
    private ContactType category;
    private String notes;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LegalEntityBasicResponse legalEntity;
    private String personalEmail;
    private SubCategoryDto subCategory;

    public ContactResponse(ContactDto contactDto){
        this.id = contactDto.getId();
        this.firstName = contactDto.getFirstName();
        this.lastName = contactDto.getLastName();
        this.email = contactDto.getEmail();
        this.phoneNumber = contactDto.getPhoneNumber();
        this.position = contactDto.getPosition();
        this.department = contactDto.getDepartment();
        this.category = contactDto.getCategory();
        this.notes = contactDto.getNotes();
        this.isActive = contactDto.getIsActive();
        this.createdAt = contactDto.getCreatedAt();
        this.updatedAt = contactDto.getUpdatedAt();
        this.legalEntity = new LegalEntityBasicResponse(contactDto.getLegalEntity());
        this.personalEmail = contactDto.getPersonalEmail();
        this.subCategory = contactDto.getSubCategory();
    }
}
