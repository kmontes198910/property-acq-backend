package com.kynsof.identity.infrastructure.entities;

import com.kynsof.identity.domain.dto.ManageRolDto;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "roles_permission",
            joinColumns = @JoinColumn(name = "rol_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

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
        this.permissions = dto.getPermissions() != null
                ? dto.getPermissions().stream().map(Permission::new).collect(Collectors.toSet())
                : Collections.emptySet();
    }

    public ManageRolDto toAggregateSimple() {
        return new ManageRolDto(
                this.id, 
                this.code, 
                this.name, 
                this.isDeleted,
                this.permissions != null ? permissions.stream().map(Permission::toAggregate).collect(Collectors.toList()) : null
        );
    }

    public ManageRolDto toAggregate() {
        return ManageRolDto
                .builder()
                .id(id)
                .code(code)
                .name(name)
                .isDeleted(isDeleted)
                .build();
    }
}
