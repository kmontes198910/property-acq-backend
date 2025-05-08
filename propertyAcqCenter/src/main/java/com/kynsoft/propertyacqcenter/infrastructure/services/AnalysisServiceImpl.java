package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.AnalysisResponse;
import com.kynsoft.propertyacqcenter.domain.dto.AnalysisDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.AnalysisNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IAnalysisService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Analysis;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.AnalysisWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.AnalysisReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class AnalysisServiceImpl implements IAnalysisService {

    private final AnalysisReadDataJPARepository repositoryQuery;
    private final AnalysisWriteDataJPARepository repositoryCommand;

    public AnalysisServiceImpl(AnalysisReadDataJPARepository repositoryQuery, 
                               AnalysisWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(AnalysisDto object) {
        return repositoryCommand.save(new Analysis(object)).getId();
    }

    @Override
    @Transactional
    public void update(AnalysisDto object) {
        Analysis update = new Analysis(object);
        repositoryCommand.save(update);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        //this.findById(id);
        repositoryCommand.deleteById(id);
    }

    @Override
    public AnalysisDto findById(UUID id) {
        Optional<Analysis> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new AnalysisNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Analysis> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Analysis> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Analysis> data) {
        List<AnalysisResponse> objects = new ArrayList<>();
        for (Analysis p : data.getContent()) {
            objects.add(new AnalysisResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
