package com.kynsof.hospitalizationService.infrastructure.service.impl;

import com.kynsof.hospitalizationService.application.response.BedResponse;
import com.kynsof.hospitalizationService.domain.dto.BedDto;
import com.kynsof.hospitalizationService.domain.dto.exception.BedNotFoundException;
import com.kynsof.hospitalizationService.domain.service.IBedService;
import com.kynsof.hospitalizationService.infrastructure.entity.Bed;
import com.kynsof.hospitalizationService.infrastructure.repositories.command.BedWriteDataJPARepository;
import com.kynsof.hospitalizationService.infrastructure.repositories.query.BedReadDataJPARepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BedServiceImpl implements IBedService {

    private final BedWriteDataJPARepository repositoryCommand;
    private final BedReadDataJPARepository repositoryQuery;

    public BedServiceImpl(BedWriteDataJPARepository repositoryCommand, BedReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    @Transactional
    public UUID create(BedDto dto) {
        Bed object = new Bed(dto);
        this.repositoryCommand.save(object);
        return dto.getId();
    }

    @Override
    @Transactional
    public UUID update(BedDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Emergency Case or ID cannot be null");
        }

        Bed update = new Bed(dto);
        this.repositoryCommand.save(update);
        return dto.getId();
    }

    @Override
    public BedDto findById(UUID id) {
        Optional<Bed> object = this.repositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }
        throw new BedNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Bed> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Bed> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Bed> data) {
        List<BedResponse> objects = new ArrayList<>();
        for (Bed p : data.getContent()) {
            objects.add(new BedResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        try {
            this.repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

}
