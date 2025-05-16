package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.AddressResponse;
import com.kynsoft.propertyacqcenter.domain.dto.AddressDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.AddressNotFoundException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.address.AddressLegalEntityIsPrimaryException;
import com.kynsoft.propertyacqcenter.domain.enums.AddressType;
import com.kynsoft.propertyacqcenter.domain.services.IAddressService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Address;
import com.kynsoft.propertyacqcenter.infrastructure.entity.LegalEntity;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.AddressWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.AddressReadDataJPARepository;
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
public class AddressServiceImpl implements IAddressService {

    private final AddressReadDataJPARepository repositoryQuery;
    private final AddressWriteDataJPARepository repositoryCommand;

    public AddressServiceImpl(AddressReadDataJPARepository repositoryQuery,
            AddressWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(AddressDto object) {
        return repositoryCommand.save(new Address(object)).getId();
    }

    @Override
    @Transactional
    public void update(AddressDto object) {
        Address update = new Address(this.findById(object.getId()));
        update.setAddressType(object.getAddressType());
        update.setCity(object.getCity());
        update.setCountry(object.getCountry());
        update.setIsPrimary(object.getIsPrimary());
        update.setLegalEntity(new LegalEntity(object.getLegalEntity()));
        update.setState(object.getState());
        update.setStreetAddress1(object.getStreetAddress1());
        update.setStreetAddress2(object.getStreetAddress2());
        update.setZipCode(object.getZipCode());
        update.setNickName(object.getNickName());

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
    public AddressDto findById(UUID id) {
        Optional<Address> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregateSimple();
        }
        throw new AddressNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Address> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Address> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Address> data) {
        List<AddressResponse> objects = new ArrayList<>();
        for (Address p : data.getContent()) {
            objects.add(new AddressResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public int countByLegalEntityAndIsPrimary(UUID legalEntity, UUID id) {
        return this.repositoryQuery.countByLegalEntityAndIsPrimary(legalEntity, AddressType.PRINCIPAL, id);
    }

    @Override
    public void validateAccountNumber(UUID legalEntity, UUID id) {
        int count = this.countByLegalEntityAndIsPrimary(legalEntity, id);
        if (count > 0) {
            throw new AddressLegalEntityIsPrimaryException();
        }
    }

}
