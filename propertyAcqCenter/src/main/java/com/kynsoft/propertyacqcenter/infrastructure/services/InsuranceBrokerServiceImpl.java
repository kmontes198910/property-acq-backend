package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.InsuranceBrokerResponse;
import com.kynsoft.propertyacqcenter.domain.dto.InsuranceBrokerDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.InsuranceBrokerNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IInsuranceBrokerService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.InsuranceBroker;
import com.kynsoft.propertyacqcenter.infrastructure.entity.LegalEntity;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Property;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.InsuranceBrokerWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.InsuranceBrokerReadDataJPARepository;
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
public class InsuranceBrokerServiceImpl implements IInsuranceBrokerService {

    private final InsuranceBrokerReadDataJPARepository repositoryQuery;
    private final InsuranceBrokerWriteDataJPARepository repositoryCommand;

    public InsuranceBrokerServiceImpl(InsuranceBrokerReadDataJPARepository repositoryQuery,
                                      InsuranceBrokerWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(InsuranceBrokerDto object) {
        return repositoryCommand.save(new InsuranceBroker(object)).getId();
    }

    @Override
    @Transactional
    public void update(InsuranceBrokerDto object) {
        InsuranceBroker update = this.findByIdSimple(object.getId());

        update.setProperty(new Property(object.getProperty()));
        update.setBuyer(new LegalEntity(object.getBuyer()));
        update.setClosingDate(object.getClosingDate());
        update.setLenderInfo(object.getLenderInfo());
        update.setLenderInsurance(object.getLenderInsurance());
        update.setUpdatedAt(LocalDateTime.now());

        repositoryCommand.save(update);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        this.findById(id);
        repositoryCommand.deleteById(id);
    }

    @Override
    public InsuranceBrokerDto findById(UUID id) {
        Optional<InsuranceBroker> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new InsuranceBrokerNotFoundException(id);
    }

    private InsuranceBroker findByIdSimple(UUID id) {
        Optional<InsuranceBroker> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new InsuranceBrokerNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<InsuranceBroker> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<InsuranceBroker> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<InsuranceBroker> data) {
        List<InsuranceBrokerResponse> objects = new ArrayList<>();
        for (InsuranceBroker p : data.getContent()) {
            objects.add(new InsuranceBrokerResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
