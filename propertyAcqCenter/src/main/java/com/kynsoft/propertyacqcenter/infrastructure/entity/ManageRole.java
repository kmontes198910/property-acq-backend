package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.ManageRolDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

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

    public ManageRole(ManageRolDto dto) {
        this.id = dto.getId();
        this.code = dto.getCode();
        this.name = dto.getName();
        this.isDeleted = dto.getIsDeleted();
    }

    public ManageRolDto toAggregate() {
        return new ManageRolDto(this.id, this.code, this.getName(), this.isDeleted);
    }

    public ManageRolDto toAggregateSimple() {
        return ManageRolDto
                .builder()
                .id(id)
                .code(code)
                .name(name)
                .build();
    }
}
