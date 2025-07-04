package com.kynsof.identity.infrastructure.entities;

import com.kynsof.identity.domain.dto.UsersRolesDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.*;

import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "r_users_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersRoles {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private  UserSystem user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private ManageRole role;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "created_by", nullable = true)
    private UUID createdBy;

    @Column(name = "updated_by", nullable = true)
    private UUID updatedBy;

    public UsersRoles(UsersRolesDto dto) {
        this.id = dto.getId();
        this.user = dto.getUser() != null ? new UserSystem(dto.getUser()) : null;
        this.role = dto.getRole() != null ? new ManageRole(dto.getRole()) : null;
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }

    public UsersRolesDto toAggregate() {
        return UsersRolesDto
                .builder()
                .id(id)
                .user(user != null ? user.toAggregateBasic() : null)
                .role(role != null ? role.toAggregateSimple() : null)
                .createdBy(createdBy)
                .updatedBy(updatedBy)
                .build();
    }
}
