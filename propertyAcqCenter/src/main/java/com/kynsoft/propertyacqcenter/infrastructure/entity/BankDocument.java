package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.BankDocumentDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "banck_document")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankDocument {

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
    @JoinColumn(name = "bank_Account")
    private BankAccount bankAccount;

    public BankDocumentDto toAggregate() {
        return BankDocumentDto.builder()
                .id(this.id)
                .createdAt(this.createdAt)
                .document(document)
                .updatedAt(this.updatedAt)
                .bankAccount(this.bankAccount != null ? this.bankAccount.toAggregateBasic() : null)
                .fileName(fileName)
                .build();
    }

    public BankDocumentDto toAggregateSimple() {
        return BankDocumentDto.builder()
                .id(this.id)
                .createdAt(this.createdAt)
                .document(document)
                .updatedAt(this.updatedAt)
                .bankAccount(this.bankAccount != null ? this.bankAccount.toAggregate() : null)
                .fileName(fileName)
                .build();
    }

    public BankDocument(BankDocumentDto dto) {
        this.id = dto.getId();
        this.document = dto.getDocument();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.bankAccount = new BankAccount(dto.getBankAccount());
        this.fileName = dto.getFileName();
    }

}
