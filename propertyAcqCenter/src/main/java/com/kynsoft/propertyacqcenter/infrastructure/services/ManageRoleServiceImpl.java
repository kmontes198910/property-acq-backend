package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.ManageRoleResponse;
import com.kynsoft.propertyacqcenter.domain.dto.ManageRolDto;
import com.kynsoft.propertyacqcenter.domain.services.IManageRoleService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.ManageRole;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.ManageRoleWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.ManageRoleReadDataJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ManageRoleServiceImpl implements IManageRoleService {

    private final ManageRoleWriteDataJPARepository command;
    private final ManageRoleReadDataJPARepository query;

    public ManageRoleServiceImpl(ManageRoleWriteDataJPARepository command, ManageRoleReadDataJPARepository query) {
        this.command = command;
        this.query = query;
    }

    @Override
    public void create(ManageRolDto dto) {
        command.save(new ManageRole(dto));
    }

    @Override
    public void update(ManageRolDto dto) {
        var update = new ManageRole(dto);
        command.save(update);
    }

    @Override
    public void delete(ManageRolDto objectDto) {
        this.command.save(new ManageRole(objectDto));
    }

    @Override
    public void deleteAll(List<UUID> roles) {
        var delete = roles.stream()
                .map(this::findById)
                .map(rol-> {
                    rol.setIsDeleted(true);
                    return new ManageRole(rol);
                })
                .toList();
        command.saveAll(delete);
    }

    @Override
    public ManageRolDto findById(UUID id) {
        return query.findById(id)
                .map(ManageRole::toAggregate)
                .orElseThrow(() -> new BusinessNotFoundException(new GlobalBusinessException(
                        DomainErrorMessage.PERMISSION_NOT_FOUND, new ErrorField("id", "Role not found."))));

    }

    @Override
    public Long countByCodeAndNotId(String name, UUID id) {
        return query.countByCodeAndNotId(name, id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        var specifications = new GenericSpecificationsBuilder<ManageRole>(filterCriteria);
        var data = query.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }
    private PaginatedResponse getPaginatedResponse(Page<ManageRole> data) {
        var permissionResponses = data.getContent().stream()
                .map(permission -> new ManageRoleResponse(permission.toAggregate()))
                .toList();
        return new PaginatedResponse(permissionResponses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }
}
