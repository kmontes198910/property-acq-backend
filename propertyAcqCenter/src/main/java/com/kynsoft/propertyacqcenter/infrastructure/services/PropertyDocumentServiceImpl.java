package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.PropertyDocumentResponse;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDocumentDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.PropertyDocumentNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyDocumentService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Property;
import com.kynsoft.propertyacqcenter.infrastructure.entity.PropertyDocument;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.PropertyDocumentWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.PropertyDocumentReadDataJPARepository;
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
public class PropertyDocumentServiceImpl implements IPropertyDocumentService {

    private final PropertyDocumentReadDataJPARepository repositoryQuery;
    private final PropertyDocumentWriteDataJPARepository repositoryCommand;

    public PropertyDocumentServiceImpl(PropertyDocumentReadDataJPARepository repositoryQuery,
                                       PropertyDocumentWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public void create(PropertyDocumentDto object) {
        repositoryCommand.save(new PropertyDocument(object));
    }

    @Override
    @Transactional
    public void update(PropertyDocumentDto object) {
        PropertyDocument update = this.findByIdSimple(object.getId());
        update.setProperty(new Property(object.getProperty()));
        update.setFileName(object.getFileName());
        update.setFilePath(object.getFilePath());
        update.setUpdatedBy(object.getUpdatedBy());
        update.setOwnersContractRead(object.getOwnersContractRead());
        update.setAssignmentOfContractRead(object.getAssignmentOfContractRead());
        update.setClosingDate(object.getClosingDate());
        update.setPlatMapOrSurvey(object.getPlatMapOrSurvey());
        update.setEarnestMoneyDeposit(object.getEarnestMoneyDeposit());

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
    public PropertyDocumentDto findById(UUID id) {
        Optional<PropertyDocument> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new PropertyDocumentNotFoundException(id);
    }

    private PropertyDocument findByIdSimple(UUID id) {
        Optional<PropertyDocument> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new PropertyDocumentNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<PropertyDocument> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<PropertyDocument> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<PropertyDocument> data) {
        List<PropertyDocumentResponse> objects = new ArrayList<>();
        for (PropertyDocument p : data.getContent()) {
            objects.add(new PropertyDocumentResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
