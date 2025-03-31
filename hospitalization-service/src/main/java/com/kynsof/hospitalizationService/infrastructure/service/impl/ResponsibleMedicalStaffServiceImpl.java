package com.kynsof.hospitalizationService.infrastructure.service.impl;

import com.kynsof.hospitalizationService.application.response.ResponsibleMedicalStaffResponse;
import com.kynsof.hospitalizationService.domain.dto.ResponsibleMedicalStaffDto;
import com.kynsof.hospitalizationService.domain.dto.exception.DiagnosisNotFoundException;
import com.kynsof.hospitalizationService.domain.service.IResponsibleMedicalStaffService;
import com.kynsof.hospitalizationService.infrastructure.entity.ResponsibleMedicalStaff;
import com.kynsof.hospitalizationService.infrastructure.repositories.command.ResponsibleMedicalStaffWriteDataJPARepository;
import com.kynsof.hospitalizationService.infrastructure.repositories.query.ResponsibleMedicalStaffReadDataJPARepository;
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
public class ResponsibleMedicalStaffServiceImpl implements IResponsibleMedicalStaffService {

    private final ResponsibleMedicalStaffWriteDataJPARepository repositoryCommand;
    private final ResponsibleMedicalStaffReadDataJPARepository repositoryQuery;

    public ResponsibleMedicalStaffServiceImpl(ResponsibleMedicalStaffWriteDataJPARepository repositoryCommand, ResponsibleMedicalStaffReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    @Transactional
    public UUID create(ResponsibleMedicalStaffDto dto) {
        ResponsibleMedicalStaff object = new ResponsibleMedicalStaff(dto);
        this.repositoryCommand.save(object);
        return dto.getId();
    }

    @Override
    @Transactional
    public UUID update(ResponsibleMedicalStaffDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Responsible Medical Staff or ID cannot be null");
        }

        ResponsibleMedicalStaff update = new ResponsibleMedicalStaff(dto);
        this.repositoryCommand.save(update);
        return dto.getId();
    }

    @Override
    public ResponsibleMedicalStaffDto findById(UUID id) {
        Optional<ResponsibleMedicalStaff> object = this.repositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }
        throw new DiagnosisNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<ResponsibleMedicalStaff> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<ResponsibleMedicalStaff> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<ResponsibleMedicalStaff> data) {
        List<ResponsibleMedicalStaffResponse> objects = new ArrayList<>();
        for (ResponsibleMedicalStaff p : data.getContent()) {
            objects.add(new ResponsibleMedicalStaffResponse(p.toAggregate()));
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
