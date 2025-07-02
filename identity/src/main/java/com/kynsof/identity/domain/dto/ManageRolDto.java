package com.kynsof.identity.domain.dto;


import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
import lombok.Builder;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ManageRolDto implements Serializable {
    private UUID id;
    private String code;
    private String name;
    private Boolean isDeleted;
    private List<PermissionDto> permissions;
}
