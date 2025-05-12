package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.CurrencyResponse;
import com.kynsoft.propertyacqcenter.domain.dto.CurrencyDto;
import com.kynsoft.propertyacqcenter.domain.services.ICurrencyService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Currency;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.CurrencyWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.CurrencyReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class CurrencyServiceImpl implements ICurrencyService {

    private final CurrencyReadDataJPARepository repositoryQuery;
    private final CurrencyWriteDataJPARepository repositoryCommand;

    public CurrencyServiceImpl(CurrencyReadDataJPARepository repositoryQuery, 
                               CurrencyWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Currency> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Currency> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Currency> data) {
        List<CurrencyResponse> objects = new ArrayList<>();
        for (Currency p : data.getContent()) {
            objects.add(new CurrencyResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public UUID create(CurrencyDto object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(CurrencyDto object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public CurrencyDto findById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
