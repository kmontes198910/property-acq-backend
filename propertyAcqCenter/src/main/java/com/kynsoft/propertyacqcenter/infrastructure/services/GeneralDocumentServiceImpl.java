package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.GeneralDocumentResponse;
import com.kynsoft.propertyacqcenter.domain.dto.GeneralDocumentDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.AcquisitionDocumentNotFoundException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.NotDeleteException;
import com.kynsoft.propertyacqcenter.domain.services.IGeneralDocumentService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.AdquisitionProperty;
import com.kynsoft.propertyacqcenter.infrastructure.entity.DocumentType;
import com.kynsoft.propertyacqcenter.infrastructure.entity.GeneralDocument;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.GeneralDocumentWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.GeneralDocumentReadDataJPARepository;
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
public class GeneralDocumentServiceImpl implements IGeneralDocumentService {

    private final GeneralDocumentReadDataJPARepository repositoryQuery;
    private final GeneralDocumentWriteDataJPARepository repositoryCommand;

    public GeneralDocumentServiceImpl(GeneralDocumentReadDataJPARepository repositoryQuery,
                                          GeneralDocumentWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(GeneralDocumentDto object) {
        return repositoryCommand.save(new GeneralDocument(object)).getId();
    }

    @Override
    @Transactional
    public void update(GeneralDocumentDto object) {
        GeneralDocument update = this.findByIdSimple(object.getId());

        update.setDocumentType(object.getDocumentType() != null ? new DocumentType(object.getDocumentType()) : null);
        update.setAdquisitionProperty(object.getAdquisitionProperty() != null ? new AdquisitionProperty(object.getAdquisitionProperty()) : null);
        update.setFileName(object.getFileName());
        update.setFilePath(object.getFilePath());
        update.setUpdatedAt(LocalDateTime.now());
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
    public GeneralDocumentDto findById(UUID id) {
        Optional<GeneralDocument> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new AcquisitionDocumentNotFoundException(id);
    }

    private GeneralDocument findByIdSimple(UUID id) {
        Optional<GeneralDocument> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new AcquisitionDocumentNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<GeneralDocument> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<GeneralDocument> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<GeneralDocument> data) {
        List<GeneralDocumentResponse> objects = new ArrayList<>();
        for (GeneralDocument p : data.getContent()) {
            objects.add(new GeneralDocumentResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
