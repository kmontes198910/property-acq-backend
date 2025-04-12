package com.kynsof.identity.infrastructure.services;

import com.kynsof.identity.application.query.module.getbyid.ModuleResponse;
import com.kynsof.identity.application.query.module.search.ModuleListResponse;
import com.kynsof.identity.domain.dto.ModuleDto;
import com.kynsof.identity.domain.dto.moduleDto.ModuleDataDto;
import com.kynsof.identity.domain.dto.moduleDto.ModuleNodeDto;
import com.kynsof.identity.domain.interfaces.service.IModuleService;
import com.kynsof.identity.infrastructure.entities.ModuleSystem;
import com.kynsof.identity.infrastructure.repository.command.ModuleWriteDataJPARepository;
import com.kynsof.identity.infrastructure.repository.query.ModuleReadDataJPARepository;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements IModuleService {

    private final ModuleWriteDataJPARepository commandRepository;
    private final ModuleReadDataJPARepository queryRepository;

    @Autowired
    public ModuleServiceImpl(ModuleWriteDataJPARepository commandRepository, ModuleReadDataJPARepository queryRepository) {
        this.commandRepository = commandRepository;
        this.queryRepository = queryRepository;
    }

    @Override
    @Transactional
    public void create(ModuleDto object) {
        commandRepository.save(new ModuleSystem(object));
    }

    @Override
    @Transactional
    public void update(ModuleDto object) {
        var update = new ModuleSystem(object);
        update.setUpdatedAt(LocalDateTime.now());
        commandRepository.save(update);
    }

    @Override
    @Transactional
    public void delete(ModuleDto delete) {
        var moduleSystem = new ModuleSystem(delete);
        moduleSystem.setName(delete.getName() + "-" + UUID.randomUUID());
        commandRepository.save(moduleSystem);
    }

    @Override
    @Transactional
    public void deleteAll(List<UUID> modules) {
        var delete = modules.stream()
                .map(this::findById)
                .map(this::createDeactivatedModule)
                .toList();
        commandRepository.saveAll(delete);
    }

    private ModuleSystem createDeactivatedModule(ModuleDto moduleDto) {
        var moduleSystem = new ModuleSystem(moduleDto);
        moduleSystem.setName(moduleDto.getName() + "-" + UUID.randomUUID());
        return moduleSystem;
    }

    @Override
    public ModuleDto findById(UUID id) {
        return queryRepository.findById(id)
                .map(ModuleSystem::toAggregate)
                .orElseThrow(() -> new BusinessNotFoundException(new GlobalBusinessException(
                        DomainErrorMessage.MODULE_NOT_FOUND, new ErrorField("id", "Module not found."))));
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
     //   filterCriteria(filterCriteria);
        var specifications = new GenericSpecificationsBuilder<ModuleResponse>(filterCriteria);
        var data = queryRepository.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }


//    private void filterCriteria(List<FilterCriteria> filterCriteria) {
//        for (FilterCriteria filter : filterCriteria) {
//
//            if ("status".equals(filter.getKey()) && filter.getValue() instanceof String) {
//                try {
//                    Status enumValue = Status.valueOf((String) filter.getValue());
//                    filter.setValue(enumValue);
//                } catch (IllegalArgumentException e) {
//                    System.err.println("Valor inválido para el tipo Enum Status: " + filter.getValue());
//                }
//            }
//        }
//    }

    private PaginatedResponse getPaginatedResponse(Page<ModuleSystem> data) {
        var moduleListResponses = data.getContent().stream()
                .map(module -> new ModuleListResponse(module.toAggregate()))
                .toList();
        return new PaginatedResponse(moduleListResponses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public List<ModuleNodeDto> buildStructure() {
        var modules = queryRepository.findAll();
        return modules.stream().map(this::createModuleNode).toList();
    }

    private ModuleNodeDto createModuleNode(ModuleSystem module) {
        var moduleNode = new ModuleNodeDto();
        moduleNode.setKey(module.getId().toString());
        moduleNode.setData(new ModuleDataDto(module.getName(), "Module", module.getName()));

        var permissionsNodes = module.getPermissions().stream()
                .map(permission -> {
                    var permissionNode = new ModuleNodeDto();
                    permissionNode.setKey(permission.getId().toString());
                    permissionNode.setData(new ModuleDataDto(permission.getDescription(), "Permission", permission.getCode()));
                    return permissionNode;
                }).toList();

        moduleNode.setChildren(permissionsNodes);
        return moduleNode;
    }

    @Override
    public Long countByNameAndNotId(String name, UUID id) {
        return queryRepository.countByNameAndNotId(name, id);
    }
}