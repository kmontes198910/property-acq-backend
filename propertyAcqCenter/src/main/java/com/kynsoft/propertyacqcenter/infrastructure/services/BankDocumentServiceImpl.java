package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.BankDocumentResponse;
import com.kynsoft.propertyacqcenter.domain.dto.BankDocumentDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.AddressNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IBankDocumentService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.BankAccount;
import com.kynsoft.propertyacqcenter.infrastructure.entity.BankDocument;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.BankDocumentWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.BankDocumentReadDataJPARepository;
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
public class BankDocumentServiceImpl implements IBankDocumentService {

    private final BankDocumentReadDataJPARepository repositoryQuery;
    private final BankDocumentWriteDataJPARepository repositoryCommand;

    public BankDocumentServiceImpl(BankDocumentReadDataJPARepository repositoryQuery, 
                              BankDocumentWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(BankDocumentDto object) {
        return repositoryCommand.save(new BankDocument(object)).getId();
    }

    @Override
    @Transactional
    public void update(BankDocumentDto object) {
        BankDocument update = new BankDocument(this.findById(object.getId()));
        update.setBankAccount(new BankAccount(object.getBankAccount()));
        update.setDocument(object.getDocument());
        update.setFileName(object.getFileName());
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
    public BankDocumentDto findById(UUID id) {
        Optional<BankDocument> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregateSimple();
        }
        throw new AddressNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<BankDocument> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<BankDocument> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<BankDocument> data) {
        List<BankDocumentResponse> objects = new ArrayList<>();
        for (BankDocument p : data.getContent()) {
            objects.add(new BankDocumentResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
