package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.ManageRoleResponse;
import com.kynsoft.propertyacqcenter.domain.dto.DocumentTypeDto;
import com.kynsoft.propertyacqcenter.domain.dto.ManageRolDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.NotDeleteException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.RoleNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IManageRoleService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.DocumentType;
import com.kynsoft.propertyacqcenter.infrastructure.entity.ManageRole;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.ManageRoleWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.ManageRoleReadDataJPARepository;
import java.util.Collections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
        ManageRole update = this.findByIdSimple(dto.getId());
        update.setCode(dto.getCode());
        update.setIsDeleted(dto.getIsDeleted());
        update.setName(dto.getName());
        update.setDocumentTypes(dto.getDocumentTypes() != null
                ? dto.getDocumentTypes().stream().map(DocumentType::new).collect(Collectors.toSet())
                : Collections.emptySet());
        command.save(update);
    }

    @Override
    public void delete(UUID id) {
        try {
            this.command.deleteById(id);
        } catch (Exception e) {
            throw new NotDeleteException();
        }
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

    private ManageRole findByIdSimple(UUID id) {
        Optional<ManageRole> entity = query.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new RoleNotFoundException(id);
    }

    @Override
    public ManageRolDto findById(UUID id) {
        return query.findById(id)
                .map(ManageRole::toAggregate)
                .orElseThrow(() -> new RoleNotFoundException(id));
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

    @Override
    public List<ManageRolDto> findRolesByEmployeeId(UUID employeeId) {
        Set<ManageRole> roles = query.findRolesByEmployeeId(employeeId);
        return roles.stream()
                .map(role -> ManageRolDto
                .builder()
                .id(role.getId())
                .code(role.getCode())
                .name(role.getName())
                .documentTypes(role.getDocumentTypes().stream()
                        .map(x -> DocumentTypeDto
                        .builder()
                        .code(x.getCode())
                        .id(x.getId())
                        .name(x.getName())
                        .build())
                        .collect(Collectors.toList()))
                .build())
                .collect(Collectors.toList());
    }
}
