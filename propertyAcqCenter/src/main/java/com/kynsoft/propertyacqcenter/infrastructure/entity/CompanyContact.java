package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.CompanyContactDto;
import com.kynsoft.propertyacqcenter.domain.enums.ContactType;
import com.kynsoft.propertyacqcenter.domain.enums.DepartmentType;
import com.kynsoft.propertyacqcenter.domain.enums.Type;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "company_contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyContact {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = true)
    private Company company;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "personal_email")
    private String personalEmail;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "position")
    private String position;

    @Column(name = "mailing_address")
    private String mailingAddress;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "department_type", nullable = true)
    private DepartmentType department;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_employee")
    private Boolean isEmployee;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    ///
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "legal_entity_id", nullable = true)
    private LegalEntity legalEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category", nullable = true)
    private SubCategory subCategory;

    @Column(name = "category", nullable = true)
    private String category;//es un enum que se almacena como String ContactType

    @Column(name = "type", nullable = true)
    private String type;//es un enum que se almacena como String (LEGAL_ENTITY or COMPANY)

    public CompanyContact(CompanyContactDto dto) {
        this.id = dto.getId();
        this.birthDate = dto.getBirthDate();
        this.company = dto.getCompany() != null ? new Company(dto.getCompany()) : null;
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
        this.legalEntity = dto.getLegalEntity() != null ? new LegalEntity(dto.getLegalEntity()) : null;
        this.subCategory = dto.getSubCategory() != null ? new SubCategory(dto.getSubCategory()) : null;
        this.category = dto.getCategory() != null ? dto.getCategory().name() : null;
        this.type = dto.getType() != null ? dto.getType().name() : null;
    }

    public CompanyContactDto toAggregateSimple() {
        return CompanyContactDto.builder()
                .id(this.id)
                .birthDate(birthDate)
                .company(company != null ? this.company.toAggregateSimple() : null)
                .legalEntity(legalEntity != null ? this.legalEntity.toAggregateBasic() : null)
                .subCategory(subCategory != null ? this.subCategory.toAggregate() : null)
                .category(category != null ? ContactType.valueOf(category) : null)
                .type(type != null ? Type.valueOf(type) : null)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .position(position)
                .department(department)
                .notes(notes)
                .isActive(isActive)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .personalEmail(personalEmail)
                .mailingAddress(mailingAddress)
                .isEmployee(isEmployee)
                .build();
    }

    public CompanyContactDto toAggregate() {
        return CompanyContactDto.builder()
                .id(this.id)
                .birthDate(birthDate)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .position(position)
                .department(department)
                .notes(notes)
                .isActive(isActive)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .company(company != null ? this.company.toAggregateBasic() : null)
                .legalEntity(legalEntity != null ? this.legalEntity.toAggregateBasic() : null)
                .subCategory(subCategory != null ? this.subCategory.toAggregate() : null)
                .category(category != null ? ContactType.valueOf(category) : null)
                .type(type != null ? Type.valueOf(type) : null)
                .personalEmail(personalEmail)
                .mailingAddress(mailingAddress)
                .isEmployee(isEmployee)
                .build();
    }

    public CompanyContactDto toAggregateTeam() {
        return CompanyContactDto.builder()
                .id(this.id)
                .birthDate(birthDate)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .position(position)
                .department(department)
                .notes(notes)
                .personalEmail(personalEmail)
                .company(company != null ? this.company.toAggregateBasic() : null)
                .legalEntity(legalEntity != null ? this.legalEntity.toAggregateBasic() : null)
                .subCategory(subCategory != null ? this.subCategory.toAggregate() : null)
                //.category(category != null ? ContactType.valueOf(category) : null)
                //.type(type != null ? Type.valueOf(type) : null)
                .mailingAddress(mailingAddress)
                .isEmployee(isEmployee)
                .build();
    }

}