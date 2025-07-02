package com.kynsof.identity.infrastructure.services;

import com.kynsof.identity.application.query.module.getbyid.ModuleResponse;
import com.kynsof.identity.application.query.permission.getById.PermissionResponse;
import com.kynsof.identity.application.query.users.userMe.BusinessPermissionResponse;
import com.kynsof.identity.application.query.users.userMe.UserMeResponse;
import com.kynsof.identity.domain.interfaces.service.IUserMeService;
import com.kynsof.identity.infrastructure.entities.ManageRole;
import com.kynsof.identity.infrastructure.entities.Permission;
import com.kynsof.identity.infrastructure.entities.UserPermissionBusiness;
import com.kynsof.identity.infrastructure.entities.UserSystem;
import com.kynsof.identity.infrastructure.repository.query.BusinessModuleReadDataJPARepository;
import com.kynsof.identity.infrastructure.repository.query.UserPermissionBusinessReadDataJPARepository;
import com.kynsof.identity.infrastructure.repository.query.UserSystemReadDataJPARepository;
import com.kynsof.share.core.domain.EUserType;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserMeServiceImpl implements IUserMeService {

    private final UserPermissionBusinessReadDataJPARepository userPermissionBusinessReadDataJPARepository;
    private final UserSystemReadDataJPARepository repositoryQuery;
    private final BusinessModuleReadDataJPARepository businessModuleReadDataJPARepository;

    public UserMeServiceImpl(UserPermissionBusinessReadDataJPARepository userPermissionBusinessReadDataJPARepository,
                             UserSystemReadDataJPARepository repositoryQuery, BusinessModuleReadDataJPARepository businessModuleReadDataJPARepository) {
        this.userPermissionBusinessReadDataJPARepository = userPermissionBusinessReadDataJPARepository;
        this.repositoryQuery = repositoryQuery;
        this.businessModuleReadDataJPARepository = businessModuleReadDataJPARepository;
    }

    @Override
    //@Cacheable(value = IdentityCacheConfig.USER_INFO_CACHE, key = "#userId", unless = "#result == null")
    public UserMeResponse getUserInfo(UUID userId) {
        var userSystem = repositoryQuery.findByKeyCloakId(userId)
                .orElseThrow(() -> new BusinessNotFoundException(new GlobalBusinessException(
                        DomainErrorMessage.USER_NOT_FOUND, new ErrorField("id", DomainErrorMessage.USER_NOT_FOUND.getReasonPhrase()))));

        if (userSystem.getUserType().equals(EUserType.SUPER_ADMIN)) {
            List<BusinessPermissionResponse> businessPermissionResponses = getAllBusinessesWithPermissions();
            return createUserMeResponse(userSystem, businessPermissionResponses);
        }

        var userPermissions = userPermissionBusinessReadDataJPARepository.findUserPermissionBusinessByUserId(userSystem.getId());
        var businessResponses = groupUserPermissionsByBusiness(userPermissions);

        return createUserMeResponse(userSystem, new ArrayList<>(businessResponses.values()));
    }

    private Map<UUID, BusinessPermissionResponse> groupUserPermissionsByBusiness(List<UserPermissionBusiness> userPermissions) {
        return userPermissions.stream()
                .collect(Collectors.groupingBy(upb -> upb.getBusiness().getId()))
                .values().stream()
                .map(userPermissionBusinesses -> {
                    var permissions = userPermissionBusinesses.stream()
                            .map(upb -> upb.getPermission().getCode())
                            .distinct()
                            .toList();
                    var business = userPermissionBusinesses.get(0).getBusiness();

                    return new BusinessPermissionResponse(
                            business.getId(),
                            business.getBalance(),
                            business.getName(),
                            business.getIdResponsible(),
                            permissions);
                })
                .collect(Collectors.toMap(BusinessPermissionResponse::getBusinessId, bpr -> bpr));
    }

    private UserMeResponse createUserMeResponse(UserSystem userSystem, List<BusinessPermissionResponse> businessResponses) {
        return new UserMeResponse(
                userSystem.getId(),
                userSystem.getUserName(),
                userSystem.getEmail(),
                userSystem.getName(),
                userSystem.getLastName(),
                userSystem.getImage(),
                userSystem.getSelectedBusiness(),
                businessResponses,
                null

        );
    }

    private List<PermissionResponse> permissionList(UserSystem userData) {
        List<PermissionResponse> permissions = new ArrayList<>();
        if (userData.getRoles() != null) {
            for (ManageRole r : userData.getRoles()) {
            if (r.getPermissions() != null) {
                for (Permission permission : r.getPermissions()) {
                    permissions.add(new PermissionResponse(
                            permission.getId(), 
                            permission.getCode(), 
                            permission.getDescription(), 
                            new ModuleResponse(permission.getModule().getId(), permission.getModule().getName()), 
                            permission.getStatus(), 
                            permission.getAction(), 
                            permission.getCreatedAt()));
                }
            }
        }
        }
        return permissions;
    }

  //  @Cacheable(value = IdentityCacheConfig.USER_INFO_CACHE, unless = "#result == null")
    public List<BusinessPermissionResponse> getAllBusinessesWithPermissions() {
        List<Object[]> results = businessModuleReadDataJPARepository.findAllBusinessesWithPermissions();
        Map<UUID, BusinessPermissionResponse> responseMap = new HashMap<>();

        for (Object[] result : results) {
            UUID businessId = (UUID) result[0];
            String businessName = (String) result[1];
            String permissionCode = (String) result[2];
            double balance = (double) result[3];
            UUID idResponsable = (UUID) result[4];

            responseMap.computeIfAbsent(businessId, id -> new BusinessPermissionResponse(id, balance, businessName,idResponsable, new ArrayList<>()))
                    .getPermissions().add(permissionCode);
        }

        return new ArrayList<>(responseMap.values());
    }
}