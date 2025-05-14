package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.OwnerDocumentDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "owner_document")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerDocument {

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
    @JoinColumn(name = "owner_document")
    private OwnerShipLegalEntity owner;

    public OwnerDocumentDto toAggregate() {
        return OwnerDocumentDto.builder()
                .id(this.id)
                .createdAt(this.createdAt)
                .document(document)
                .updatedAt(this.updatedAt)
                .owner(this.owner != null ? this.owner.toAggregate() : null)
                .fileName(fileName)
                .build();
    }

    public OwnerDocumentDto toAggregateBasic() {
        return OwnerDocumentDto.builder()
                .id(this.id)
                .build();
    }

    public OwnerDocument(OwnerDocumentDto dto) {
        this.id = dto.getId();
        this.document = dto.getDocument();
        this.owner = new OwnerShipLegalEntity(dto.getOwner());
        this.fileName = dto.getFileName();
    }

}
