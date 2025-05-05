package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.CompanyContactDto;
import jakarta.persistence.*;
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
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "position")
    private String position;

    @Column(name = "department")
    private String department;

    @Column(name = "category")
    private String category;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "is_active")
    private Boolean isActive;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public CompanyContact(CompanyContactDto dto) {
        this.id = dto.getId();
        this.company = dto.getCompany() != null ? new Company(dto.getCompany()) : null;
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
    }

    public CompanyContactDto toAggregateSimple() {
        return CompanyContactDto.builder()
                .id(this.id)
                .company(company != null ? this.company.toAggregateSimple() : null)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .position(position)
                .department(department)
                .category(category)
                .notes(notes)
                .isActive(isActive)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    public CompanyContactDto toAggregate() {
        return CompanyContactDto.builder()
                .id(this.id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .position(position)
                .department(department)
                .category(category)
                .notes(notes)
                .isActive(isActive)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

}