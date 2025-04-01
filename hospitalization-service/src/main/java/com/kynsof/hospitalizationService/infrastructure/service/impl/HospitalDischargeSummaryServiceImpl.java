package com.kynsof.hospitalizationService.infrastructure.service.impl;

import com.kynsof.hospitalizationService.application.response.HospitalDischargeSummaryResponse;
import com.kynsof.hospitalizationService.domain.dto.HospitalDischargeSummaryDto;
import com.kynsof.hospitalizationService.domain.dto.exception.DiagnosisNotFoundException;
import com.kynsof.hospitalizationService.domain.service.IHospitalDischargeSummaryService;
import com.kynsof.hospitalizationService.infrastructure.entity.HospitalDischargeSummary;
import com.kynsof.hospitalizationService.infrastructure.repositories.command.HospitalDischargeSummaryWriteDataJPARepository;
import com.kynsof.hospitalizationService.infrastructure.repositories.query.HospitalDischargeSummaryReadDataJPARepository;
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
public class HospitalDischargeSummaryServiceImpl implements IHospitalDischargeSummaryService {

    private final HospitalDischargeSummaryWriteDataJPARepository repositoryCommand;
    private final HospitalDischargeSummaryReadDataJPARepository repositoryQuery;

    public HospitalDischargeSummaryServiceImpl(HospitalDischargeSummaryWriteDataJPARepository repositoryCommand, HospitalDischargeSummaryReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    @Transactional
    public UUID create(HospitalDischargeSummaryDto dto) {
        HospitalDischargeSummary object = new HospitalDischargeSummary(dto);
        this.repositoryCommand.save(object);
        return dto.getId();
    }

    @Override
    @Transactional
    public UUID update(HospitalDischargeSummaryDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Emergency Case or ID cannot be null");
        }

        HospitalDischargeSummary update = new HospitalDischargeSummary(dto);
        this.repositoryCommand.save(update);
        return dto.getId();
    }

    @Override
    public HospitalDischargeSummaryDto findById(UUID id) {
        Optional<HospitalDischargeSummary> object = this.repositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }
        throw new DiagnosisNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<HospitalDischargeSummary> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<HospitalDischargeSummary> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<HospitalDischargeSummary> data) {
        List<HospitalDischargeSummaryResponse> objects = new ArrayList<>();
        for (HospitalDischargeSummary p : data.getContent()) {
            objects.add(new HospitalDischargeSummaryResponse(p.toAggregate()));
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
