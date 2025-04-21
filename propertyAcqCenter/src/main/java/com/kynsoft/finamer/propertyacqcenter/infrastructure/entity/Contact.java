package com.kynsoft.finamer.propertyacqcenter.infrastructure.entity;

import com.kynsoft.finamer.propertyacqcenter.domain.dto.ContactDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

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
    
    @Column(name = "company")
    private String company;
    
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "legal_entity_id")
    private LegalEntity legalEntity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    private Business business;
    
    /**
     * Método para convertir la entidad a DTO
     * @return ContactDto con los datos de esta entidad
     */
    public ContactDto toAggregate() {
        return ContactDto.builder()
                .id(this.id)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .position(this.position)
                .department(this.department)
                .category(this.category)
                .company(this.company)
                .notes(this.notes)
                .isActive(this.isActive)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .legalEntityId(this.legalEntity != null ? this.legalEntity.getId() : null)
                .businessId(this.business != null ? this.business.getId() : null)
                .build();
    }
    
    /**
     * Constructor a partir de un DTO
     * @param dto ContactDto con los datos a utilizar
     */
    public Contact(ContactDto dto) {
        this.id = dto.getId();
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.email = dto.getEmail();
        this.phoneNumber = dto.getPhoneNumber();
        this.position = dto.getPosition();
        this.department = dto.getDepartment();
        this.category = dto.getCategory();
        this.company = dto.getCompany();
        this.notes = dto.getNotes();
        this.isActive = dto.getIsActive();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        // Las relaciones con LegalEntity y Business deben ser establecidas externamente
    }
}