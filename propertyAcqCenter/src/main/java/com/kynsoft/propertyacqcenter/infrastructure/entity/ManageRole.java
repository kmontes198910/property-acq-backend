package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.ManageRolDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "manage_role")
public class ManageRole {

    @Id
    @Column(name = "id")
    private UUID id;
    @Column(unique = true)
    private String code;
    private String name;
    private Boolean isDeleted;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "roles_document_type",
            joinColumns = @JoinColumn(name = "rol_id"),
            inverseJoinColumns = @JoinColumn(name = "document_type_id")
    )
    private Set<DocumentType> documentTypes = new HashSet<>();

    public ManageRole(ManageRolDto dto) {
        this.id = dto.getId();
        this.code = dto.getCode();
        this.name = dto.getName();
        this.isDeleted = dto.getIsDeleted();
        this.documentTypes = dto.getDocumentTypes()!= null
                ? dto.getDocumentTypes().stream().map(DocumentType::new).collect(Collectors.toSet())
                : Collections.emptySet();
    }

    public ManageRolDto toAggregate() {
        return ManageRolDto
                .builder()
                .id(id)
                .code(code)
                .name(name)
                .documentTypes(documentTypes != null ? documentTypes.stream().map(DocumentType::toAggregate).collect(Collectors.toList()) : null)
                .isDeleted(isDeleted)
                .build();
    }

    public ManageRolDto toAggregateSimple() {
        return ManageRolDto
                .builder()
                .id(id)
                .code(code)
                .name(name)
                .documentTypes(documentTypes != null ? documentTypes.stream().map(DocumentType::toAggregate).collect(Collectors.toList()) : null)
                .build();
    }
}
