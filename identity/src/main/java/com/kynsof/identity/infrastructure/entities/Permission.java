package com.kynsof.identity.infrastructure.entities;

import com.kynsof.identity.domain.dto.PermissionDto;
import com.kynsof.identity.domain.dto.enumType.PermissionStatusEnm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Permission {

    @Id
    @Column(name = "id")
    private UUID id;
    @Column(unique = true)
    private String code;
    private String description;
    private String action;

    @Enumerated(EnumType.STRING)
    private PermissionStatusEnm status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "module_id")
    private ModuleSystem module;

    @OneToMany(mappedBy = "permission")
    private Set<UserPermissionBusiness> userPermissionBusinesses = new HashSet<>();

    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserTypePermission> userTypePermissions = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Permission(PermissionDto permissionDto) {
        this.id = permissionDto.getId();
        this.code = permissionDto.getCode();
        this.description = permissionDto.getDescription();
        this.action = permissionDto.getAction();
        this.module = permissionDto.getModule() != null ? new ModuleSystem(permissionDto.getModule()) : null;
        this.status = permissionDto.getStatus();
    }

    public PermissionDto toAggregate() {
        return new PermissionDto(
                this.id,
                this.code,
                this.description,
                this.module != null ? this.module.toAggregateSimple() : null,
                this.status,
                this.action,
                createdAt
        );
    }
}
