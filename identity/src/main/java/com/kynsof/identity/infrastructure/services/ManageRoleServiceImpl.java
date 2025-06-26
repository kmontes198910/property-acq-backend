package com.kynsof.identity.infrastructure.services;

import com.kynsof.identity.domain.dto.exception.manageRole.ManageRoleCodeException;
import com.kynsof.identity.application.query.manageRole.getByid.ManageRoleResponse;
import com.kynsof.identity.domain.dto.ManageRolDto;
import com.kynsof.identity.domain.dto.exception.manageRole.ManageRoleCodeIsNullException;
import com.kynsof.identity.domain.interfaces.service.IManageRoleService;
import com.kynsof.identity.infrastructure.entities.ManageRole;
import com.kynsof.identity.infrastructure.repository.command.ManageRoleWriteDataJPARepository;
import com.kynsof.identity.infrastructure.repository.query.ManageRoleReadDataJPARepository;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
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
    public ManageRolDto create(ManageRolDto dto) {
        return command.save(new ManageRole(dto)).toAggregate();
    }

    @Override
    public ManageRolDto update(ManageRolDto dto) {
        var update = new ManageRole(dto);
        return command.save(update).toAggregate();
    }

    @Override
    public void delete(ManageRolDto objectDto) {
        //this.command.save(new ManageRole(objectDto));
        this.command.deleteById(objectDto.getId());
    }

    @Override
    public void deleteAll(List<UUID> roles) {
        var delete = roles.stream()
                .map(this::findById)
                .map(rol -> {
                    rol.setIsDeleted(true);
                    return new ManageRole(rol);
                })
                .toList();
        command.saveAll(delete);
    }

    @Override
    public ManageRolDto findById(UUID id) {
        return query.findById(id)
                .map(ManageRole::toAggregateSimple)
                .orElseThrow(() -> new BusinessNotFoundException(new GlobalBusinessException(
                DomainErrorMessage.PERMISSION_NOT_FOUND, new ErrorField("id", "Role not found."))));

    }

    @Override
    public Long countByCodeAndNotId(String code, UUID id) {
        return query.countByCodeAndNotId(code, id);
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

    @Override
    public void validateCode(String code, UUID id) {
        Long count = this.countByCodeAndNotId(code, id);
        if (count > 0) {
            throw new ManageRoleCodeException(code);
        }
    }

    @Override
    public void validateNull(String code) {
        if (code == null || code.isEmpty()) {
            throw new ManageRoleCodeIsNullException();
        }
    }
}
