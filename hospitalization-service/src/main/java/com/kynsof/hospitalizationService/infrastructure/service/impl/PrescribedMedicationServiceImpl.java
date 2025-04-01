package com.kynsof.hospitalizationService.infrastructure.service.impl;

import com.kynsof.hospitalizationService.application.response.PrescribedMedicationResponse;
import com.kynsof.hospitalizationService.domain.dto.PrescribedMedicationDto;
import com.kynsof.hospitalizationService.domain.dto.exception.PrescribedMedicationNotFoundException;
import com.kynsof.hospitalizationService.domain.service.IPrescribedMedicationService;
import com.kynsof.hospitalizationService.infrastructure.entity.PrescribedMedication;
import com.kynsof.hospitalizationService.infrastructure.repositories.command.PrescribedMedicationWriteDataJPARepository;
import com.kynsof.hospitalizationService.infrastructure.repositories.query.PrescribedMedicationReadDataJPARepository;
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
public class PrescribedMedicationServiceImpl implements IPrescribedMedicationService {

    private final PrescribedMedicationWriteDataJPARepository repositoryCommand;
    private final PrescribedMedicationReadDataJPARepository repositoryQuery;

    public PrescribedMedicationServiceImpl(PrescribedMedicationWriteDataJPARepository repositoryCommand, PrescribedMedicationReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    @Transactional
    public UUID create(PrescribedMedicationDto dto) {
        PrescribedMedication object = new PrescribedMedication(dto);
        this.repositoryCommand.save(object);
        return dto.getId();
    }

    @Override
    @Transactional
    public UUID update(PrescribedMedicationDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Emergency Case or ID cannot be null");
        }

        PrescribedMedication update = new PrescribedMedication(dto);
        this.repositoryCommand.save(update);
        return dto.getId();
    }

    @Override
    public PrescribedMedicationDto findById(UUID id) {
        Optional<PrescribedMedication> object = this.repositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }
        throw new PrescribedMedicationNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<PrescribedMedication> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<PrescribedMedication> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<PrescribedMedication> data) {
        List<PrescribedMedicationResponse> objects = new ArrayList<>();
        for (PrescribedMedication p : data.getContent()) {
            objects.add(new PrescribedMedicationResponse(p.toAggregate()));
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
