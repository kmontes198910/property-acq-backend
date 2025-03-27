package com.kynsof.treatments.application.service;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsof.treatments.application.query.medicine.getbyid.MedicinesResponse;
import com.kynsof.treatments.domain.dto.MedicinesDto;
import com.kynsof.treatments.domain.service.IMedicinesService;
import com.kynsof.treatments.infrastructure.entity.Medicines;
import com.kynsof.treatments.infrastructure.entity.Procedure;
import com.kynsof.treatments.infrastructure.repositories.command.MedicinesWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.query.MedicinesReadDataJPARepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MedicinesServiceImpl implements IMedicinesService {

    private final MedicinesReadDataJPARepository repositoryQuery;
    private final MedicinesWriteDataJPARepository repositoryCommand;

    public MedicinesServiceImpl(MedicinesReadDataJPARepository repositoryQuery, MedicinesWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
  //  @CacheEvict(value = "medicines", allEntries = true)
    public void create(MedicinesDto medicines) {
        this.repositoryCommand.save(new Medicines(medicines));
    }

    @Override
   // @CacheEvict(value = "medicines", allEntries = true)
    public void update(MedicinesDto medicines) {
        Medicines update = new Medicines(medicines);
        update.setUpdatedAt(LocalDateTime.now());
        this.repositoryCommand.save(update);
    }

    @Override
   // @CacheEvict(value = "medicines", allEntries = true)
    public void delete(MedicinesDto object) {
        try {
            this.repositoryCommand.deleteById(object.getId());
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(
                    DomainErrorMessage.NOT_DELETE,
                    new ErrorField("id", "Element cannot be deleted as it has related elements.")
            ));
        }
    }

    @Override
   // @Cacheable(value = "medicines", key = "#id", unless = "#result == null")
    public MedicinesDto findById(UUID id) {
        Optional<Medicines> object = this.repositoryQuery.findById(id);
        return object.map(Medicines::toAggregate)
                .orElseThrow(() -> new BusinessNotFoundException(
                        new GlobalBusinessException(DomainErrorMessage.MEDICINES_NOT_FOUND,
                                new ErrorField("id", "Medicine not found."))));
    }

    @Override
   // @Cacheable(value = "medicines", key = "#pageable.pageNumber + '-' + #pageable.pageSize", unless = "#result.getTotalElements() == 0")
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Procedure> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Medicines> data = this.repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Medicines> data) {
        List<MedicinesResponse> medicinesResponses = data.getContent().stream()
                .map(medicine -> new MedicinesResponse(medicine.toAggregate()))
                .collect(Collectors.toList());

        return new PaginatedResponse(
                medicinesResponses,
                data.getTotalPages(),
                data.getNumberOfElements(),
                data.getTotalElements(),
                data.getSize(),
                data.getNumber()
        );
    }

    @Override
    public Long countByNameAndNotId(String name, UUID id) {
        return this.repositoryQuery.countByNameAndNotId(name, id);
    }
}