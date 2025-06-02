package com.kynsof.identity.infrastructure.services;

import com.kynsof.identity.domain.interfaces.service.IUserTypePermissionService;
import com.kynsof.identity.infrastructure.entities.UserTypePermission;
import com.kynsof.identity.infrastructure.repository.query.UserTypePermissionReadDataJPARepository;
import com.kynsof.share.core.domain.EUserType;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserTypePermissionService implements IUserTypePermissionService {

    private final UserTypePermissionReadDataJPARepository userTypePermissionRepository;

    public UserTypePermissionService(UserTypePermissionReadDataJPARepository userTypePermissionRepository) {
        this.userTypePermissionRepository = userTypePermissionRepository;
    }

    @Override
    public List<UserTypePermission> getPermissionsByUserType(EUserType userType) {
        return userTypePermissionRepository.findByUserType(userType);
    }
}
