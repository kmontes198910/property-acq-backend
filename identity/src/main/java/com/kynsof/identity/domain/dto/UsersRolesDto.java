package com.kynsof.identity.domain.dto;

import java.time.LocalDateTime;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersRolesDto {

    private UUID id;
    private UserSystemDto user;
    private ManageRolDto role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
}
