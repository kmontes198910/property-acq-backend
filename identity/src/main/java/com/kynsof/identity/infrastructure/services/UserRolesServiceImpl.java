package com.kynsof.identity.infrastructure.services;

import com.kynsof.identity.controller.exception.ManageRole.UserSystemSetRolesDto;
import com.kynsof.identity.domain.dto.ManageRolDto;
import com.kynsof.identity.domain.dto.PermissionDto;
import com.kynsof.identity.domain.interfaces.service.IUsersRolesService;
import com.kynsof.identity.infrastructure.entities.ManageRole;
import com.kynsof.identity.infrastructure.entities.Permission;
import com.kynsof.identity.infrastructure.entities.UserSystem;
import com.kynsof.identity.infrastructure.entities.UsersRoles;
import com.kynsof.identity.infrastructure.repository.command.UsersRolesWriteDataJPARepository;
import com.kynsof.identity.infrastructure.repository.query.ManageRoleReadDataJPARepository;
import com.kynsof.identity.infrastructure.repository.query.UserSystemReadDataJPARepository;
import com.kynsof.identity.infrastructure.repository.query.UsersRolesReadDataJPARepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRolesServiceImpl implements IUsersRolesService {

    private final UsersRolesReadDataJPARepository repositoryQuery;
    private final UsersRolesWriteDataJPARepository repositoryCommand;

    private final UserSystemReadDataJPARepository userSystemRead;
    private final ManageRoleReadDataJPARepository roleRead;

    public UserRolesServiceImpl(UsersRolesReadDataJPARepository repositoryQuery,
                                UsersRolesWriteDataJPARepository repositoryCommand,
                                UserSystemReadDataJPARepository userSystemRead,
                                ManageRoleReadDataJPARepository roleRead) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
        this.userSystemRead = userSystemRead;
        this.roleRead = roleRead;
    }

    @Override
    @Transactional
    public void create(UserSystemSetRolesDto object) {
        this.repositoryCommand.deleteByUserId(object.getId());
        List<ManageRole> roles = this.roleRead.findByIds(object.getRoles());
        Optional<UserSystem> user = this.userSystemRead.findById(object.getId());
        List<UsersRoles> list = new ArrayList<>();
        if (user.isPresent()) {
            for (ManageRole role : roles) {
                list.add(UsersRoles
                        .builder()
                        .id(UUID.randomUUID())
                        .role(role)
                        .user(user.get())
                        .build());
            }
        }

        this.repositoryCommand.saveAll(list);
    }

    @Override
    public List<PermissionDto> permissions(UUID userId) {
        List<UsersRoles> list = this.repositoryQuery.findByUserId(userId);
        List<PermissionDto> permissions = new ArrayList<>();
        if (list != null) {
            for (UsersRoles usersRoles : list) {
                if (usersRoles.getRole().getPermissions() != null) {
                    for (Permission permission : usersRoles.getRole().getPermissions()) {
                        permissions.add(permission.toAggregate());
                    }
                }
            }
        }
        return permissions;
    }

    @Override
    public List<ManageRolDto> roles(UUID userId) {
        List<UsersRoles> list = this.repositoryQuery.findByUserId(userId);
        List<ManageRolDto> roles = new ArrayList<>();
        if (list != null) {
            for (UsersRoles usersRoles : list) {
                roles.add(usersRoles.getRole().toAggregateSimple());
            }
        }
        return roles;
    }
}
