package com.kynsof.identity.domain.interfaces.service;

import com.kynsof.identity.controller.exception.ManageRole.UserSystemSetRolesDto;
import com.kynsof.identity.domain.dto.PermissionDto;
import java.util.List;
import java.util.UUID;

public interface IUsersRolesService {
    void create(UserSystemSetRolesDto object);
    List<PermissionDto> permissions(UUID userId);
}