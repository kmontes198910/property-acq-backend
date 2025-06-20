package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.GeneralDocumentDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "general_document")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralDocument {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id")
    private DocumentType documentType;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_path", nullable = false)
    private String filePath;

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

    public GeneralDocument(GeneralDocumentDto dto) {
        this.id = dto.getId() != null ? dto.getId() : UUID.randomUUID();
        this.documentType = dto.getDocumentType() != null ? new DocumentType(dto.getDocumentType()) : null;
        this.fileName = dto.getFileName();
        this.filePath = dto.getFilePath();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }

    public GeneralDocumentDto toAggregate() {
        return GeneralDocumentDto.builder()
                .id(this.id)
                .fileName(this.fileName)
                .filePath(this.filePath)
                .documentType(this.documentType.toAggregate())
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .build();
    }
}