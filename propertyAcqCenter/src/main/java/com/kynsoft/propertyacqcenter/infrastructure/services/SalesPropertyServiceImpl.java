package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.SalesPropertyResponse;
import com.kynsoft.propertyacqcenter.domain.dto.SalesPropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.PurchaseForPropertyNotFoundException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.PurchaseNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.ISalesPropertyService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Property;
import com.kynsoft.propertyacqcenter.infrastructure.entity.SalesProperty;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.SalesPropertyWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.SalesPropertyReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class SalesPropertyServiceImpl implements ISalesPropertyService {

    private final SalesPropertyReadDataJPARepository repositoryQuery;
    private final SalesPropertyWriteDataJPARepository repositoryCommand;

    public SalesPropertyServiceImpl(SalesPropertyReadDataJPARepository repositoryQuery,
                               SalesPropertyWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(SalesPropertyDto object) {
        return repositoryCommand.save(new SalesProperty(object)).getId();
    }

    @Override
    @Transactional
    public void update(SalesPropertyDto object) {
        SalesProperty update = this.findByIdSimple(object.getId());

        update.setProperty(new Property(object.getProperty()));
        update.setCapitalGainsTaxRate(object.getCapitalGainsTaxRate());
        update.setDeprecationDoubleDecliningBalance(object.getDeprecationDoubleDecliningBalance());
        update.setDeprecationNone(object.getDeprecationNone());
        update.setDeprecationStraightline(object.getDeprecationStraightline());
        update.setFederalIncomeTaxRate(object.getFederalIncomeTaxRate());
        update.setIsCapRate(object.getIsCapRate());
        update.setIsFixedSellingPrice(object.getIsFixedSellingPrice());
        update.setIsInflationRate(object.getIsInflationRate());
        update.setIsMarketValue(object.getIsMarketValue());
        update.setIsMarketValueIndreaseRate(object.getIsMarketValueIndreaseRate());
        update.setIsPurchesePrice(object.getIsPurchesePrice());
        update.setMarketValueIndreaseRate(object.getMarketValueIndreaseRate());
        update.setOther(object.getOther());
        update.setPurchesePrice(object.getPurchesePrice());
        update.setSalesCostFixedDollarAmount(object.getSalesCostFixedDollarAmount());
        update.setSalesCostNone(object.getSalesCostNone());
        update.setSalesCostPercentage(object.getSalesCostPercentage());
        update.setStateIncomeTaxRate(object.getStateIncomeTaxRate());

        repositoryCommand.save(update);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        this.findByIdSimple(id);
        repositoryCommand.deleteById(id);
    }

    @Override
    public SalesPropertyDto findById(UUID id) {
        Optional<SalesProperty> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new PurchaseNotFoundException(id);
    }

    private SalesProperty findByIdSimple(UUID id) {
        Optional<SalesProperty> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new PurchaseNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<SalesProperty> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<SalesProperty> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<SalesProperty> data) {
        List<SalesPropertyResponse> objects = new ArrayList<>();
        for (SalesProperty p : data.getContent()) {
            objects.add(new SalesPropertyResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public SalesPropertyDto findByPropertyId(String propertyId) {
        Optional<SalesProperty> entity = repositoryQuery.findByPropertyId(propertyId);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new PurchaseForPropertyNotFoundException();
    }
}
