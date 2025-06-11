package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.InsuranceResponse;
import com.kynsoft.propertyacqcenter.domain.dto.InsuranceDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.InsuranceNotFoundException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.NotDeleteException;
import com.kynsoft.propertyacqcenter.domain.services.IInsuranceService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Insurance;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.InsuranceWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.InsuranceReadDataJPARepository;
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
public class InsuranceServiceImpl implements IInsuranceService {

    private final InsuranceReadDataJPARepository repositoryQuery;
    private final InsuranceWriteDataJPARepository repositoryCommand;

    public InsuranceServiceImpl(InsuranceReadDataJPARepository repositoryQuery, 
                               InsuranceWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(InsuranceDto object) {
        return repositoryCommand.save(new Insurance(object)).getId();
    }

    @Override
    @Transactional
    public void update(InsuranceDto object) {
        Insurance business = new Insurance(object);
        business.setUpdatedAt(LocalDateTime.now());
        repositoryCommand.save(business);
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
    public InsuranceDto findById(UUID id) {
        Optional<Insurance> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new InsuranceNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Insurance> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Insurance> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Insurance> data) {
        List<InsuranceResponse> objects = new ArrayList<>();
        for (Insurance p : data.getContent()) {
            objects.add(new InsuranceResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
