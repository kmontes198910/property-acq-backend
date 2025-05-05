package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.ContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
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
    private String department;
    private String category;
    private String notes;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LegalEntityDto legalEntity;

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
        this.legalEntity = contactDto.getLegalEntity();
    }
}
