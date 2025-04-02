package com.kynsof.hospitalizationService.infrastructure.service.impl;

import com.kynsof.hospitalizationService.application.response.MedicalPrescriptionResponse;
import com.kynsof.hospitalizationService.domain.dto.MedicalPrescriptionDto;
import com.kynsof.hospitalizationService.domain.dto.exception.DiagnosisNotFoundException;
import com.kynsof.hospitalizationService.domain.dto.exception.MedicalPrescriptionDtoNotFoundException;
import com.kynsof.hospitalizationService.domain.service.IMedicalPrescriptionService;
import com.kynsof.hospitalizationService.infrastructure.entity.MedicalPrescription;
import com.kynsof.hospitalizationService.infrastructure.repositories.command.MedicalPrescriptionWriteDataJPARepository;
import com.kynsof.hospitalizationService.infrastructure.repositories.query.MedicalPrescriptionReadDataJPARepository;
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
public class MedicalPrescriptionServiceImpl implements IMedicalPrescriptionService {

    private final MedicalPrescriptionWriteDataJPARepository repositoryCommand;
    private final MedicalPrescriptionReadDataJPARepository repositoryQuery;

    public MedicalPrescriptionServiceImpl(MedicalPrescriptionWriteDataJPARepository repositoryCommand, MedicalPrescriptionReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    @Transactional
    public UUID create(MedicalPrescriptionDto dto) {
        MedicalPrescription object = new MedicalPrescription(dto);
        this.repositoryCommand.save(object);
        return dto.getId();
    }

    @Override
    @Transactional
    public UUID update(MedicalPrescriptionDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Medical Prescription or ID cannot be null");
        }

        MedicalPrescription update = new MedicalPrescription(dto);
        this.repositoryCommand.save(update);
        return dto.getId();
    }

    @Override
    public MedicalPrescriptionDto findById(UUID id) {
        Optional<MedicalPrescription> object = this.repositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }
        throw new MedicalPrescriptionDtoNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<MedicalPrescription> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<MedicalPrescription> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<MedicalPrescription> data) {
        List<MedicalPrescriptionResponse> objects = new ArrayList<>();
        for (MedicalPrescription p : data.getContent()) {
            objects.add(new MedicalPrescriptionResponse(p.toAggregate()));
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
