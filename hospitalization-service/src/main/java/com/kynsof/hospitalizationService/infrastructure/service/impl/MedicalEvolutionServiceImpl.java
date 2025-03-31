package com.kynsof.hospitalizationService.infrastructure.service.impl;

import com.kynsof.hospitalizationService.application.response.MedicalEvolutionResponse;
import com.kynsof.hospitalizationService.domain.dto.MedicalEvolutionDto;
import com.kynsof.hospitalizationService.domain.dto.exception.MedicalEvolutionNotFoundException;
import com.kynsof.hospitalizationService.domain.service.IMedicalEvolutionService;
import com.kynsof.hospitalizationService.infrastructure.entity.MedicalEvolution;
import com.kynsof.hospitalizationService.infrastructure.repositories.command.MedicalEvolutionWriteDataJPARepository;
import com.kynsof.hospitalizationService.infrastructure.repositories.query.MedicalEvolutionReadDataJPARepository;
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
public class MedicalEvolutionServiceImpl implements IMedicalEvolutionService {

    private final MedicalEvolutionWriteDataJPARepository repositoryCommand;
    private final MedicalEvolutionReadDataJPARepository repositoryQuery;

    public MedicalEvolutionServiceImpl(MedicalEvolutionWriteDataJPARepository repositoryCommand, MedicalEvolutionReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    @Transactional
    public UUID create(MedicalEvolutionDto dto) {
        MedicalEvolution object = new MedicalEvolution(dto);
        this.repositoryCommand.save(object);
        return dto.getId();
    }

    @Override
    @Transactional
    public UUID update(MedicalEvolutionDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Medical Evolution or ID cannot be null");
        }

        MedicalEvolution update = new MedicalEvolution(dto);
        this.repositoryCommand.save(update);
        return dto.getId();
    }

    @Override
    public MedicalEvolutionDto findById(UUID id) {
        Optional<MedicalEvolution> object = this.repositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }
        throw new MedicalEvolutionNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<MedicalEvolution> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<MedicalEvolution> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<MedicalEvolution> data) {
        List<MedicalEvolutionResponse> objects = new ArrayList<>();
        for (MedicalEvolution p : data.getContent()) {
            objects.add(new MedicalEvolutionResponse(p.toAggregate()));
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
