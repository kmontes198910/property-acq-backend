package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.DocumentDto;
import com.kynsoft.propertyacqcenter.domain.enums.DocumentType;
import com.kynsoft.propertyacqcenter.domain.enums.DocumentStatus;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "legal_entity_id", nullable = false)
    private LegalEntity legalEntity;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private DocumentType documentType;

    @Column(name = "description")
    private String description;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;
    
    @Column(name = "issued_by")
    private String issuedBy;
    
    @Column(name = "issuing_date")
    private LocalDate issuingDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "document_status")
    private DocumentStatus documentStatus;
    
    @Column(name = "verification_date")
    private LocalDate verificationDate;
    
    @Column(name = "verified_by")
    private UUID verifiedBy;
    
    @Column(name = "document_number")
    private String documentNumber;
    
    @Column(name = "is_original")
    private Boolean isOriginal;
    
    @Column(name = "version")
    private String version;

    @Column(name = "renewal_date")
    private LocalDate renewalDate;
    
    @Column(name = "tags")
    private String tags;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    public Document(DocumentDto dto) {
        this.id = dto.getId() != null ? dto.getId() : UUID.randomUUID();
        this.legalEntity = dto.getLegalEntity() != null ? new LegalEntity(dto.getLegalEntity()) : null;
        this.fileName = dto.getFileName();
        this.filePath = dto.getFilePath();
        this.documentType = dto.getDocumentType();
        this.description = dto.getDescription();
        this.fileSize = dto.getFileSize();
        this.contentType = dto.getContentType();
        this.expirationDate = dto.getExpirationDate();
        this.issuedBy = dto.getIssuedBy();
        this.issuingDate = dto.getIssuingDate();
        this.documentStatus = dto.getDocumentStatus();
        this.verificationDate = dto.getVerificationDate();
        this.verifiedBy = dto.getVerifiedBy();
        this.documentNumber = dto.getDocumentNumber();
        this.isOriginal = dto.getIsOriginal();
        this.version = dto.getVersion();
        this.renewalDate = dto.getRenewalDate();
        this.tags = dto.getTags();
        this.notes = dto.getNotes();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }

    public DocumentDto toAggregate() {
        return DocumentDto.builder()
                .id(this.id)
                .fileName(this.fileName)
                .filePath(this.filePath)
                .documentType(this.documentType)
                .description(this.description)
                .fileSize(this.fileSize)
                .contentType(this.contentType)
                .expirationDate(this.expirationDate)
                .issuedBy(this.issuedBy)
                .issuingDate(this.issuingDate)
                .documentStatus(this.documentStatus)
                .verificationDate(this.verificationDate)
                .verifiedBy(this.verifiedBy)
                .documentNumber(this.documentNumber)
                .isOriginal(this.isOriginal)
                .version(this.version)
                .renewalDate(this.renewalDate)
                .tags(this.tags)
                .notes(this.notes)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .legalEntity(this.legalEntity.toAggregateBasic())
                .build();
    }

    public DocumentDto toAggregateFindById() {
        return DocumentDto.builder()
                .id(this.id)
                .legalEntity(this.legalEntity.toAggregate())
                .fileName(this.fileName)
                .filePath(this.filePath)
                .documentType(this.documentType)
                .description(this.description)
                .fileSize(this.fileSize)
                .contentType(this.contentType)
                .expirationDate(this.expirationDate)
                .issuedBy(this.issuedBy)
                .issuingDate(this.issuingDate)
                .documentStatus(this.documentStatus)
                .verificationDate(this.verificationDate)
                .verifiedBy(this.verifiedBy)
                .documentNumber(this.documentNumber)
                .isOriginal(this.isOriginal)
                .version(this.version)
                .renewalDate(this.renewalDate)
                .tags(this.tags)
                .notes(this.notes)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .build();
    }
}