package com.kynsof.identity.domain.interfaces.service;

import com.kynsof.identity.domain.dto.ManageRolDto;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IManageRoleService {
    ManageRolDto create(ManageRolDto dto);

    ManageRolDto update(ManageRolDto dto);

    void delete(ManageRolDto objectDto);

    void deleteAll(List<UUID> roles);

    ManageRolDto findById(UUID id);

    Long countByCodeAndNotId(String name, UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}
