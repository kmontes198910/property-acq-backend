package com.kynsof.identity.controller.exception.ManageRole;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSystemSetRolesDto {
    private UUID id;
    private List<UUID> roles;
}
