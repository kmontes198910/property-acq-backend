package com.kynsof.hospitalizationService.infrastructure.service.impl;

import com.kynsof.hospitalizationService.application.response.MedicalStaffResponse;
import com.kynsof.hospitalizationService.domain.dto.MedicalStaffDto;
import com.kynsof.hospitalizationService.domain.dto.exception.MedicalStaffNotFoundException;
import com.kynsof.hospitalizationService.domain.service.IMedicalStaffService;
import com.kynsof.hospitalizationService.infrastructure.entity.MedicalStaff;
import com.kynsof.hospitalizationService.infrastructure.repositories.command.MedicalStaffWriteDataJPARepository;
import com.kynsof.hospitalizationService.infrastructure.repositories.query.MedicalStaffReadDataJPARepository;
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
public class MedicalStaffServiceImpl implements IMedicalStaffService {

    private final MedicalStaffWriteDataJPARepository repositoryCommand;
    private final MedicalStaffReadDataJPARepository repositoryQuery;

    public MedicalStaffServiceImpl(MedicalStaffWriteDataJPARepository repositoryCommand, MedicalStaffReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    @Transactional
    public UUID create(MedicalStaffDto dto) {
        MedicalStaff object = new MedicalStaff(dto);
        this.repositoryCommand.save(object);
        return dto.getId();
    }

    @Override
    @Transactional
    public UUID update(MedicalStaffDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Medical Staff or ID cannot be null");
        }

        MedicalStaff update = new MedicalStaff(dto);
        this.repositoryCommand.save(update);
        return dto.getId();
    }

    @Override
    public MedicalStaffDto findById(UUID id) {
        Optional<MedicalStaff> object = this.repositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }
        throw new MedicalStaffNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<MedicalStaff> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<MedicalStaff> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<MedicalStaff> data) {
        List<MedicalStaffResponse> objects = new ArrayList<>();
        for (MedicalStaff p : data.getContent()) {
            objects.add(new MedicalStaffResponse(p.toAggregate()));
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
