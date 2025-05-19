package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.PropertyImagesResponse;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyImagesDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.PropertyImagesNotFoundException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.propertyImagen.PropertyImagenMainMustBeUniqueException;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyImagesService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Property;
import com.kynsoft.propertyacqcenter.infrastructure.entity.PropertyImages;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.PropertyImagesWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.PropertyImagesReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class PropertyImagesServiceImpl implements IPropertyImagesService {

    private final PropertyImagesReadDataJPARepository repositoryQuery;
    private final PropertyImagesWriteDataJPARepository repositoryCommand;

    public PropertyImagesServiceImpl(PropertyImagesReadDataJPARepository repositoryQuery, 
                                     PropertyImagesWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Transactional
    @Override
    public UUID create(PropertyImagesDto object) {
        return repositoryCommand.save(new PropertyImages(object)).getId();
    }

    @Override
    @Transactional
    public void update(PropertyImagesDto object) {
        PropertyImages update = new PropertyImages(object);
        update.setId(object.getId());
        update.setProperty(object.getProperty() != null ? new Property(object.getProperty()) : null);
        update.setUrl(object.getUrl());
        update.setMain(object.getMain());
        repositoryCommand.save(update);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        this.getById(id);
        repositoryCommand.deleteById(id);
    }

    @Override
    public PropertyImagesDto getById(UUID id) {
        Optional<PropertyImages> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregateSimple();
        }
        throw new PropertyImagesNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<PropertyImages> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<PropertyImages> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<PropertyImages> data) {
        List<PropertyImagesResponse> objects = new ArrayList<>();
        for (PropertyImages p : data.getContent()) {
            objects.add(new PropertyImagesResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public int countByPropertyAndIsMain(String property) {
        return this.repositoryQuery.countByPropertyAndIsMain(property);
    }

    @Override
    public void validatePropertyImagenMain(String property) {
        int count = this.countByPropertyAndIsMain(property);
        if (count > 0) {
            throw new PropertyImagenMainMustBeUniqueException(property);
        }
    }

}
