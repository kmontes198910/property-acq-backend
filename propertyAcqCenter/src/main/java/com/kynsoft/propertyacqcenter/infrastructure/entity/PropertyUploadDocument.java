package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.PropertyUploadDocumentDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "property_upload_document")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyUploadDocument {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "document", nullable = false)
    private String document;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_document")
    private Property property;

    public PropertyUploadDocumentDto toAggregate() {
        return PropertyUploadDocumentDto.builder()
                .id(this.id)
                .createdAt(this.createdAt)
                .document(document)
                .updatedAt(this.updatedAt)
                .property(this.property != null ? this.property.toAggregateBasic() : null)
                .fileName(fileName)
                .build();
    }

    public PropertyUploadDocumentDto toAggregateBasic() {
        return PropertyUploadDocumentDto.builder()
                .id(this.id)
                .build();
    }

    public PropertyUploadDocument(PropertyUploadDocumentDto dto) {
        this.id = dto.getId();
        this.document = dto.getDocument();
        this.property = new Property(dto.getProperty());
        this.fileName = dto.getFileName();
    }

}
