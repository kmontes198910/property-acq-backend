package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.LegalEntityResponse;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.LegalEntityNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Business;
import com.kynsoft.propertyacqcenter.infrastructure.entity.LegalEntity;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.LegalEntityWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.LegalEntityReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class LegalEntityServiceImpl implements ILegalEntityService {

    private final LegalEntityReadDataJPARepository repositoryQuery;
    private final LegalEntityWriteDataJPARepository repositoryCommand;

    public LegalEntityServiceImpl(LegalEntityReadDataJPARepository repositoryQuery, 
                                  LegalEntityWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(LegalEntityDto legalEntityDto) {
        return repositoryCommand.save(new LegalEntity(legalEntityDto)).getId();
    }

    @Override
    @Transactional
    public void update(LegalEntityDto legalEntityDto) {
        LegalEntity update = this.findByIdSimple(legalEntityDto.getId());

        update.setBusiness(new Business(legalEntityDto.getBusiness()));
        update.setDateOfLastAnnualReport(legalEntityDto.getDateOfLastAnnualReport());
        update.setEntityType(legalEntityDto.getEntityType());
        update.setFiscalYearEnd(legalEntityDto.getFiscalYearEnd());
        update.setFormationDate(legalEntityDto.getFormationDate());
        update.setFormationState(legalEntityDto.getFormationState());
        update.setIndustry(legalEntityDto.getIndustry());
        update.setName(legalEntityDto.getName());
        update.setNotes(legalEntityDto.getNotes());
        update.setParentEntityId(legalEntityDto.getParentEntityId());
        update.setRegistrationNumber(legalEntityDto.getRegistrationNumber());
        update.setStatus(legalEntityDto.getStatus());
        update.setTaxId(legalEntityDto.getTaxId());
        update.setWebsite(legalEntityDto.getWebsite());
        update.setBusinessDescription(legalEntityDto.getBusinessDescription());
        update.setAnnualRevenue(legalEntityDto.getAnnualRevenue());
        update.setUpdatedAt(LocalDateTime.now());

        repositoryCommand.save(update);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        this.findByIdSimple(id);
        repositoryCommand.deleteById(id);
    }

    @Override
    public LegalEntityDto findById(UUID id) {
        Optional<LegalEntity> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregateFindById();
        }
        throw new LegalEntityNotFoundException("id", id.toString());
    }

    private LegalEntity findByIdSimple(UUID id) {
        Optional<LegalEntity> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new LegalEntityNotFoundException("id", id.toString());
    }

    @Override
    public LegalEntityDto findByTaxId(String taxId) {
        Optional<LegalEntity> entity = repositoryQuery.findByTaxId(taxId);
        return entity.map(LegalEntity::toAggregate).orElse(null);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<LegalEntity> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<LegalEntity> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<LegalEntity> data) {
        List<LegalEntityResponse> objects = new ArrayList<>();
        for (LegalEntity p : data.getContent()) {
            objects.add(new LegalEntityResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public long countByTaxId(String taxId) {
        return this.repositoryQuery.countByTaxId(taxId);
    }
}
