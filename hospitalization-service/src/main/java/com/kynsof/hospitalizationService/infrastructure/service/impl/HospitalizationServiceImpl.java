package com.kynsof.hospitalizationService.infrastructure.service.impl;

import com.kynsof.hospitalizationService.application.response.HospitalizationResponse;
import com.kynsof.hospitalizationService.domain.dto.HospitalizationDto;
import com.kynsof.hospitalizationService.domain.dto.exception.DiagnosisNotFoundException;
import com.kynsof.hospitalizationService.domain.dto.exception.HospitalizationNotFoundException;
import com.kynsof.hospitalizationService.domain.service.IHospitalizationService;
import com.kynsof.hospitalizationService.infrastructure.entity.Hospitalization;
import com.kynsof.hospitalizationService.infrastructure.repositories.command.HospitalizationWriteDataJPARepository;
import com.kynsof.hospitalizationService.infrastructure.repositories.query.HospitalizationReadDataJPARepository;
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
public class HospitalizationServiceImpl implements IHospitalizationService {

    private final HospitalizationWriteDataJPARepository repositoryCommand;
    private final HospitalizationReadDataJPARepository repositoryQuery;

    public HospitalizationServiceImpl(HospitalizationWriteDataJPARepository repositoryCommand, HospitalizationReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    @Transactional
    public UUID create(HospitalizationDto dto) {
        Hospitalization object = new Hospitalization(dto);
        this.repositoryCommand.save(object);
        return dto.getId();
    }

    @Override
    @Transactional
    public UUID update(HospitalizationDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Hospitalization or ID cannot be null");
        }

        Hospitalization update = new Hospitalization(dto);
        this.repositoryCommand.save(update);
        return dto.getId();
    }

    @Override
    public HospitalizationDto findById(UUID id) {
        Optional<Hospitalization> object = this.repositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }
        throw new HospitalizationNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Hospitalization> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Hospitalization> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Hospitalization> data) {
        List<HospitalizationResponse> objects = new ArrayList<>();
        for (Hospitalization p : data.getContent()) {
            objects.add(new HospitalizationResponse(p.toAggregate()));
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
