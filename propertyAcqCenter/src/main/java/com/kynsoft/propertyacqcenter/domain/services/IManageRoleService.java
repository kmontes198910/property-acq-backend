package com.kynsoft.propertyacqcenter.domain.services;


import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.ManageRolDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IManageRoleService {
    void create(ManageRolDto dto);

    void update(ManageRolDto dto);

    void delete(ManageRolDto objectDto);

    void deleteAll(List<UUID> roles);

    ManageRolDto findById(UUID id);

    Long countByCodeAndNotId(String name, UUID id);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}
