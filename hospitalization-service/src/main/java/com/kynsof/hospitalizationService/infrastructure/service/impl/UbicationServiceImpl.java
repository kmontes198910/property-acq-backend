package com.kynsof.hospitalizationService.infrastructure.service.impl;

import com.kynsof.hospitalizationService.application.response.UbicationResponse;
import com.kynsof.hospitalizationService.domain.dto.UbicationDto;
import com.kynsof.hospitalizationService.domain.dto.exception.UbicationNotFoundException;
import com.kynsof.hospitalizationService.domain.service.IUbicationService;
import com.kynsof.hospitalizationService.infrastructure.entity.Ubication;
import com.kynsof.hospitalizationService.infrastructure.repositories.command.UbicationWriteDataJPARepository;
import com.kynsof.hospitalizationService.infrastructure.repositories.query.UbicationReadDataJPARepository;
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
public class UbicationServiceImpl implements IUbicationService {

    private final UbicationWriteDataJPARepository repositoryCommand;
    private final UbicationReadDataJPARepository repositoryQuery;

    public UbicationServiceImpl(UbicationWriteDataJPARepository repositoryCommand, UbicationReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    @Transactional
    public UUID create(UbicationDto dto) {
        Ubication object = new Ubication(dto);
        this.repositoryCommand.save(object);
        return dto.getId();
    }

    @Override
    @Transactional
    public UUID update(UbicationDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Ubication or ID cannot be null");
        }

        Ubication update = new Ubication(dto);
        this.repositoryCommand.save(update);
        return dto.getId();
    }

    @Override
    public UbicationDto findById(UUID id) {
        Optional<Ubication> object = this.repositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }
        throw new UbicationNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Ubication> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Ubication> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Ubication> data) {
        List<UbicationResponse> objects = new ArrayList<>();
        for (Ubication p : data.getContent()) {
            objects.add(new UbicationResponse(p.toAggregate()));
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
