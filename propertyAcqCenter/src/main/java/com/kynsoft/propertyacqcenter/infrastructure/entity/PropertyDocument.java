package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.PropertyDocumentDto;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "property_documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyDocument {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

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

    private Boolean ownersContractRead;
    private Boolean assignmentOfContractRead;
    private LocalDate closingDate;
    private String platMapOrSurvey;
    private String earnestMoneyDeposit;

    public PropertyDocument(PropertyDocumentDto dto) {
        this.id = dto.getId() != null ? dto.getId() : UUID.randomUUID();
        this.property = dto.getProperty() != null ? new Property(dto.getProperty()) : null;
        this.fileName = dto.getFileName();
        this.filePath = dto.getFilePath();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
        this.ownersContractRead = dto.getOwnersContractRead();
        this.assignmentOfContractRead = dto.getAssignmentOfContractRead();
        this.closingDate = dto.getClosingDate();
        this.platMapOrSurvey = dto.getPlatMapOrSurvey();
        this.earnestMoneyDeposit = dto.getEarnestMoneyDeposit();
    }

    public PropertyDocumentDto toAggregate() {
        return PropertyDocumentDto.builder()
                .id(this.id)
                .fileName(this.fileName)
                .filePath(this.filePath)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .property(this.property.toAggregateBasic())
                .ownersContractRead(ownersContractRead)
                .assignmentOfContractRead(assignmentOfContractRead)
                .closingDate(closingDate)
                .platMapOrSurvey(platMapOrSurvey)
                .earnestMoneyDeposit(earnestMoneyDeposit)
                .build();
    }
}