package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.DocumentResponse;
import com.kynsoft.propertyacqcenter.domain.dto.DocumentDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.DocumentNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IDocumentService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Document;
import com.kynsoft.propertyacqcenter.infrastructure.entity.LegalEntity;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.DocumentWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.DocumentReadDataJPARepository;
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
public class DocumentServiceImpl implements IDocumentService {

    private final DocumentReadDataJPARepository repositoryQuery;
    private final DocumentWriteDataJPARepository repositoryCommand;

    public DocumentServiceImpl(DocumentReadDataJPARepository repositoryQuery, 
                               DocumentWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(DocumentDto object) {
        return repositoryCommand.save(new Document(object)).getId();
    }

    @Override
    @Transactional
    public void update(DocumentDto object) {
        Document document = this.findByIdSimple(object.getId());
        document.setContentType(object.getContentType());
        document.setDescription(object.getDescription());
        document.setDocumentNumber(object.getDocumentNumber());
        document.setDocumentStatus(object.getDocumentStatus());
        document.setDocumentType(object.getDocumentType());
        document.setExpirationDate(object.getExpirationDate());
        document.setFileName(object.getFileName());
        document.setFilePath(object.getFilePath());
        document.setFileSize(object.getFileSize());
        document.setIsOriginal(object.getIsOriginal());
        document.setIssuedBy(object.getIssuedBy());
        document.setIssuingDate(object.getIssuingDate());
        document.setLegalEntity(new LegalEntity(object.getLegalEntity()));
        document.setNotes(object.getNotes());
        document.setRenewalDate(object.getRenewalDate());
        document.setTags(object.getTags());
        document.setVerificationDate(object.getVerificationDate());
        document.setVerifiedBy(object.getVerifiedBy());
        document.setVersion(object.getVersion());
        document.setUpdatedAt(LocalDateTime.now());
        repositoryCommand.save(document);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        this.findByIdSimple(id);
        repositoryCommand.deleteById(id);
    }

    @Override
    public DocumentDto findById(UUID id) {
        Optional<Document> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregateFindById();
        }
        throw new DocumentNotFoundException(id);
    }

    private Document findByIdSimple(UUID id) {
        Optional<Document> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new DocumentNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Document> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Document> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Document> data) {
        List<DocumentResponse> objects = new ArrayList<>();
        for (Document p : data.getContent()) {
            objects.add(new DocumentResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
