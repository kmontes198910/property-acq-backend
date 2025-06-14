package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.PropertyUploadDocumentResponse;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyUploadDocumentDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.NotDeleteException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.PropertyUploadDocumentNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyUploadDocumentService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Property;
import com.kynsoft.propertyacqcenter.infrastructure.entity.PropertyUploadDocument;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.PropertyUploadDocumentWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.PropertyUploadDocumentReadDataJPARepository;
import java.time.LocalDateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class PropertyUploadDocumentServiceImpl implements IPropertyUploadDocumentService {

    private final PropertyUploadDocumentReadDataJPARepository repositoryQuery;
    private final PropertyUploadDocumentWriteDataJPARepository repositoryCommand;

    public PropertyUploadDocumentServiceImpl(PropertyUploadDocumentReadDataJPARepository repositoryQuery, 
                                             PropertyUploadDocumentWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(PropertyUploadDocumentDto object) {
        return repositoryCommand.save(new PropertyUploadDocument(object)).getId();
    }

    @Override
    @Transactional
    public void update(PropertyUploadDocumentDto object) {
        PropertyUploadDocument update = this.findByIdSimple(object.getId());
        update.setDocument(object.getDocument());
        update.setFileName(object.getFileName());
        update.setProperty(new Property(object.getProperty()));
        update.setUpdatedAt(LocalDateTime.now());
        update.setFilePath(object.getFilePath());
        update.setDocumentType(object.getDocumentType());

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
    public PropertyUploadDocumentDto findById(UUID id) {
        Optional<PropertyUploadDocument> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new PropertyUploadDocumentNotFoundException(id);
    }

    private PropertyUploadDocument findByIdSimple(UUID id) {
        Optional<PropertyUploadDocument> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new PropertyUploadDocumentNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<PropertyUploadDocument> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<PropertyUploadDocument> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<PropertyUploadDocument> data) {
        List<PropertyUploadDocumentResponse> objects = new ArrayList<>();
        for (PropertyUploadDocument p : data.getContent()) {
            objects.add(new PropertyUploadDocumentResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
