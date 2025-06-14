package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.OwnerDocumentResponse;
import com.kynsoft.propertyacqcenter.domain.dto.OwnerDocumentDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.AddressNotFoundException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.NotDeleteException;
import com.kynsoft.propertyacqcenter.domain.services.IOwnerDocumentService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.OwnerDocument;
import com.kynsoft.propertyacqcenter.infrastructure.entity.OwnerShipLegalEntity;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.OwnerDocumentWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.OwnerDocumentReadDataJPARepository;
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
public class OwnerDocumentServiceImpl implements IOwnerDocumentService {

    private final OwnerDocumentReadDataJPARepository repositoryQuery;
    private final OwnerDocumentWriteDataJPARepository repositoryCommand;

    public OwnerDocumentServiceImpl(OwnerDocumentReadDataJPARepository repositoryQuery, 
                                    OwnerDocumentWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(OwnerDocumentDto object) {
        return repositoryCommand.save(new OwnerDocument(object)).getId();
    }

    @Override
    @Transactional
    public void update(OwnerDocumentDto object) {
        OwnerDocument update = new OwnerDocument(this.findById(object.getId()));
        update.setDocument(object.getDocument());
        update.setFileName(object.getFileName());
        update.setOwner(new OwnerShipLegalEntity(object.getOwner()));
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
    public OwnerDocumentDto findById(UUID id) {
        Optional<OwnerDocument> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new AddressNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<OwnerDocument> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<OwnerDocument> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<OwnerDocument> data) {
        List<OwnerDocumentResponse> objects = new ArrayList<>();
        for (OwnerDocument p : data.getContent()) {
            objects.add(new OwnerDocumentResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
