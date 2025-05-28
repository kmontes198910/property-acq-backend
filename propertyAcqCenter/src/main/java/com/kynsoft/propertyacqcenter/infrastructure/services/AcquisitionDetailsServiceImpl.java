package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.AcquisitionDetailsResponse;
import com.kynsoft.propertyacqcenter.domain.dto.AcquisitionDetailsDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.AcquisitionDetailsNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IAcquisitionDetailsService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.AcquisitionDetails;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Company;
import com.kynsoft.propertyacqcenter.infrastructure.entity.LegalEntity;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Property;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.AcquisitionDetailsWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.AcquisitionDetailsReadDataJPARepository;
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
public class AcquisitionDetailsServiceImpl implements IAcquisitionDetailsService {

    private final AcquisitionDetailsReadDataJPARepository repositoryQuery;
    private final AcquisitionDetailsWriteDataJPARepository repositoryCommand;

    public AcquisitionDetailsServiceImpl(AcquisitionDetailsReadDataJPARepository repositoryQuery,
                               AcquisitionDetailsWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(AcquisitionDetailsDto object) {
        return repositoryCommand.save(new AcquisitionDetails(object)).getId();
    }

    @Override
    @Transactional
    public void update(AcquisitionDetailsDto object) {
        AcquisitionDetails update = this.findByIdSimple(object.getId());
        update.setContractExecutionDate(object.getContractExecutionDate());
        update.setAcquisitionType(object.getAcquisitionType());
        update.setAfterRepairValue(object.getAfterRepairValue());
        update.setAskingPrice(object.getAskingPrice());
        update.setCreatedBy(object.getCreatedBy());
        update.setEmdOfferedAmount(object.getEmdOfferedAmount());
        update.setEmdRequirements(object.getEmdRequirements());
        update.setExpectedClosingDate(object.getExpectedClosingDate());
        update.setFloodZoneDetermination(object.getFloodZoneDetermination());
        update.setPropertyRented(object.getPropertyRented());
        update.setPurchasePrice(object.getPurchasePrice());
        update.setRentalPrice(object.getRentalPrice());
        update.setSellerContactInfo(new Company(object.getSellerContactInfo()));
        update.setSellerName(new LegalEntity(object.getSellerName()));
        update.setSourceType(object.getSourceType());
        update.setUpdatedBy(object.getUpdatedBy());
        update.setUpdatedAt(LocalDateTime.now());
        update.setProperty(new Property(object.getProperty()));

        repositoryCommand.save(update);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        this.findByIdSimple(id);
        repositoryCommand.deleteById(id);
    }

    @Override
    public AcquisitionDetailsDto findById(UUID id) {
        Optional<AcquisitionDetails> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new AcquisitionDetailsNotFoundException(id);
    }

    private AcquisitionDetails findByIdSimple(UUID id) {
        Optional<AcquisitionDetails> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new AcquisitionDetailsNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<AcquisitionDetails> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<AcquisitionDetails> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<AcquisitionDetails> data) {
        List<AcquisitionDetailsResponse> objects = new ArrayList<>();
        for (AcquisitionDetails p : data.getContent()) {
            objects.add(new AcquisitionDetailsResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }
}
