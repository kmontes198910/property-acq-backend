package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.MortgageResponse;
import com.kynsoft.propertyacqcenter.domain.dto.MortgageDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.MortgageNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IMortgageService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Mortgage;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Property;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.MortgageWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.MortgageReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class MortgageServiceImpl implements IMortgageService {

    private final MortgageReadDataJPARepository repositoryQuery;
    private final MortgageWriteDataJPARepository repositoryCommand;

    public MortgageServiceImpl(MortgageReadDataJPARepository repositoryQuery,
                               MortgageWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(MortgageDto object) {
        return repositoryCommand.save(new Mortgage(object)).getId();
    }

    @Override
    @Transactional
    public void update(MortgageDto object) {
        Mortgage update = this.findByIdSimple(object.getId());
        update.setProperty(new Property(object.getProperty()));
        update.setMortgageType(object.getMortgageType());
        update.setMortgageAmount(object.getMortgageAmount());
        update.setDownPayment(object.getDownPayment());
        update.setFixedRateTermYears(object.getFixedRateTermYears());
        update.setFixedMortgageRatePercentage(object.getFixedMortgageRatePercentage());
        update.setFirstPaymentDate(object.getFirstPaymentDate());
        update.setCompoundFrequency(object.getCompoundFrequency());
        update.setBalloonPayment(object.getBalloonPayment());
        update.setAdjustableRateDetails(object.getAdjustableRateDetails());

        repositoryCommand.save(update);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        this.findByIdSimple(id);
        repositoryCommand.deleteById(id);
    }

    @Override
    public MortgageDto findById(UUID id) {
        Optional<Mortgage> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new MortgageNotFoundException(id);
    }

    private Mortgage findByIdSimple(UUID id) {
        Optional<Mortgage> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new MortgageNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Mortgage> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Mortgage> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Mortgage> data) {
        List<MortgageResponse> objects = new ArrayList<>();
        for (Mortgage p : data.getContent()) {
            objects.add(new MortgageResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }
}
