package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.BankAccountFindByIdResponse;
import com.kynsoft.propertyacqcenter.domain.dto.BankAccountDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.BankAccountNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IBankAccountService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.BankAccount;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embeddedEntity.BankBranch;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embeddedEntity.BankContact;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embeddedEntity.InternationalBankingDetails;
import com.kynsoft.propertyacqcenter.infrastructure.entity.LegalEntity;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.BankAccountWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.BankAccountReadDataJPARepository;
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
public class BankAccountServiceImpl implements IBankAccountService {

    private final BankAccountReadDataJPARepository repositoryQuery;
    private final BankAccountWriteDataJPARepository repositoryCommand;

    public BankAccountServiceImpl(BankAccountReadDataJPARepository repositoryQuery, 
                                  BankAccountWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(BankAccountDto dto) {
        return repositoryCommand.save(new BankAccount(dto)).getId();
    }

    @Override
    @Transactional
    public void update(BankAccountDto dto) {
        BankAccount update = this.findByIdSimple(dto.getId());
        update.setAccountNickname(dto.getAccountNickname());
        update.setAccountNumber(dto.getAccountNumber());
        update.setAccountType(dto.getAccountType());
        update.setBankName(dto.getBankName());
        update.setNotes(dto.getNotes());
        update.setOnlineBankingUrl(dto.getOnlineBankingUrl());
        update.setOpeningDate(dto.getOpeningDate());
        update.setRoutingNumber(dto.getRoutingNumber());
        update.setBranchInfo(new BankBranch(dto.getBranchInfo()));
        update.setContactDetails(new BankContact(dto.getContactDetails()));
        update.setLegalEntity(new LegalEntity(dto.getLegalEntity()));
        update.setInternationalDetails(new InternationalBankingDetails(dto.getInternationalDetails()));

        update.setUpdatedAt(LocalDateTime.now());

        repositoryCommand.save(update);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        this.findByIdSimple(id);
        repositoryCommand.deleteById(id);
    }

    @Override
    public BankAccountDto findById(UUID id) {
        Optional<BankAccount> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new BankAccountNotFoundException(id);
    }

    private BankAccount findByIdSimple(UUID id) {
        Optional<BankAccount> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new BankAccountNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<BankAccount> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<BankAccount> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<BankAccount> data) {
        List<BankAccountFindByIdResponse> objects = new ArrayList<>();
        for (BankAccount p : data.getContent()) {
            objects.add(new BankAccountFindByIdResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }
}
