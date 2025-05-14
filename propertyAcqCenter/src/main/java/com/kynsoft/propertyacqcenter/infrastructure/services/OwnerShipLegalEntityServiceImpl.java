package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.OwnerShipLegalEntityResponse;
import com.kynsoft.propertyacqcenter.domain.dto.OwnerShipLegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.AddressNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IOwnerShipLegalEntityService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.LegalEntity;
import com.kynsoft.propertyacqcenter.infrastructure.entity.OwnerShipLegalEntity;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.OwnerShipLegalEntityWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.OwnerShipLegalEntityReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class OwnerShipLegalEntityServiceImpl implements IOwnerShipLegalEntityService {

    private final OwnerShipLegalEntityReadDataJPARepository repositoryQuery;
    private final OwnerShipLegalEntityWriteDataJPARepository repositoryCommand;

    public OwnerShipLegalEntityServiceImpl(OwnerShipLegalEntityReadDataJPARepository repositoryQuery, 
                              OwnerShipLegalEntityWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(OwnerShipLegalEntityDto object) {
        return repositoryCommand.save(new OwnerShipLegalEntity(object)).getId();
    }

    @Override
    @Transactional
    public void update(OwnerShipLegalEntityDto object) {
        OwnerShipLegalEntity update = new OwnerShipLegalEntity(this.findById(object.getId()));
        update.setName(object.getName());
        update.setOwnershipPercentage(object.getOwnershipPercentage());
        update.setLegalEntity(new LegalEntity(object.getLegalEntity()));

        repositoryCommand.save(update);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        this.findById(id);
        repositoryCommand.deleteById(id);
    }

    @Override
    public OwnerShipLegalEntityDto findById(UUID id) {
        Optional<OwnerShipLegalEntity> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregateBasic();
        }
        throw new AddressNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<OwnerShipLegalEntity> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<OwnerShipLegalEntity> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<OwnerShipLegalEntity> data) {
        List<OwnerShipLegalEntityResponse> objects = new ArrayList<>();
        for (OwnerShipLegalEntity p : data.getContent()) {
            objects.add(new OwnerShipLegalEntityResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
