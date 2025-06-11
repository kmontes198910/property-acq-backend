package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.IncomeDetailsBreakdownResponse;
import com.kynsoft.propertyacqcenter.domain.dto.IncomeDetailsBreakdownDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.AddressNotFoundException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.NotDeleteException;
import com.kynsoft.propertyacqcenter.domain.services.IIncomeDetailsBreakdownService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.IncomeDetailsBreakdown;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.IncomeDetailsBreakdownWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.IncomeDetailsBreakdownReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class IncomeDetailsBreakdownServiceImpl implements IIncomeDetailsBreakdownService {

    private final IncomeDetailsBreakdownReadDataJPARepository repositoryQuery;
    private final IncomeDetailsBreakdownWriteDataJPARepository repositoryCommand;

    public IncomeDetailsBreakdownServiceImpl(IncomeDetailsBreakdownReadDataJPARepository repositoryQuery,
            IncomeDetailsBreakdownWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(IncomeDetailsBreakdownDto object) {
        return repositoryCommand.save(new IncomeDetailsBreakdown(object)).getId();
    }

    @Override
    @Transactional
    public void update(IncomeDetailsBreakdownDto object) {
        IncomeDetailsBreakdown update = this.findByIdSimple(object.getId());
        
        repositoryCommand.save(update);
    }

    @Override
    public void delete(UUID id) {
        try {
            this.findById(id);
            repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new NotDeleteException();
        }
    }

    @Override
    public IncomeDetailsBreakdownDto findById(UUID id) {
        Optional<IncomeDetailsBreakdown> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new AddressNotFoundException(id);
    }

    private IncomeDetailsBreakdown findByIdSimple(UUID id) {
        Optional<IncomeDetailsBreakdown> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new AddressNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<IncomeDetailsBreakdown> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<IncomeDetailsBreakdown> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<IncomeDetailsBreakdown> data) {
        List<IncomeDetailsBreakdownResponse> objects = new ArrayList<>();
        for (IncomeDetailsBreakdown p : data.getContent()) {
            objects.add(new IncomeDetailsBreakdownResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
