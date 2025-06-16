package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.DocumentTypeResponse;
import com.kynsoft.propertyacqcenter.domain.dto.DocumentTypeDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.DocumentTypeNotFoundException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.NotDeleteException;
import com.kynsoft.propertyacqcenter.domain.services.IDocumentTypeService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.DocumentType;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.DocumentTypeWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.DocumentTypeReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class DocumentTypeServiceImpl implements IDocumentTypeService {

    private final DocumentTypeReadDataJPARepository repositoryQuery;
    private final DocumentTypeWriteDataJPARepository repositoryCommand;

    public DocumentTypeServiceImpl(DocumentTypeReadDataJPARepository repositoryQuery,
            DocumentTypeWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(DocumentTypeDto object) {
        return repositoryCommand.save(new DocumentType(object)).getId();
    }

    @Override
    @Transactional
    public void update(DocumentTypeDto object) {
        DocumentType update = this.findByIdSimple(object.getId());
        update.setCode(object.getCode());
        update.setName(object.getName());

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
    public DocumentTypeDto findById(UUID id) {
        Optional<DocumentType> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new DocumentTypeNotFoundException(id);
    }

    private DocumentType findByIdSimple(UUID id) {
        Optional<DocumentType> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new DocumentTypeNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<DocumentType> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<DocumentType> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<DocumentType> data) {
        List<DocumentTypeResponse> objects = new ArrayList<>();
        for (DocumentType p : data.getContent()) {
            objects.add(new DocumentTypeResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
