package com.kynsof.payment.infrastructure.service;

import com.kynsof.payment.application.query.accountReconciliation.search.AccountReconciliationResponse;
import com.kynsof.payment.domain.dto.AccountReconciliationDto;
import com.kynsof.payment.domain.dto.enumDto.BillingStatus;
import com.kynsof.payment.domain.service.IAccountReconciliationService;
import com.kynsof.payment.infrastructure.entity.AccountReconciliation;
import com.kynsof.payment.infrastructure.repositories.command.AccountReconciliationWriteDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.query.AccountReconciliationReadDataJPARepository;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountReconciliationServiceImpl implements IAccountReconciliationService {

    private final AccountReconciliationReadDataJPARepository repositoryQuery;

    private final AccountReconciliationWriteDataJPARepository repositoryCommand;

    public AccountReconciliationServiceImpl(AccountReconciliationReadDataJPARepository repositoryQuery, AccountReconciliationWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public void create(AccountReconciliationDto dto) {
        this.repositoryCommand.save(new AccountReconciliation(dto));
    }

    @Override
    @Transactional
    public void update(AccountReconciliationDto billingDto) {
        AccountReconciliation object = this.repositoryQuery.findById(billingDto.getId()).orElseThrow();
        object.setCode(billingDto.getCode());
        object.setDescription(billingDto.getDescription());
        object.setCost(billingDto.getCost());
        this.repositoryCommand.save(object);
    }

    @Override
    public void delete(AccountReconciliationDto object) {
        try {
            this.repositoryCommand.deleteById(object.getId());
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        filterCriteria(filterCriteria);
        GenericSpecificationsBuilder<AccountReconciliation> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<AccountReconciliation> data = this.repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private void filterCriteria(List<FilterCriteria> filterCriteria) {
        filterCriteria.forEach(filter -> {
            if ("status".equals(filter.getKey()) && filter.getValue() instanceof String) {
                filter.setValue(parseEnum(BillingStatus.class, (String) filter.getValue(), "BillingStatus"));
            }
        });
    }

    private <T extends Enum<T>> T parseEnum(Class<T> enumClass, String value, String enumName) {
        try {
            return Enum.valueOf(enumClass, value);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid value for enum " + enumName + ": " + value);
            return null;
        }
    }

    private PaginatedResponse getPaginatedResponse(Page<AccountReconciliation> data) {
        List<AccountReconciliationResponse> patients = new ArrayList<>();
        for (AccountReconciliation o : data.getContent()) {
            patients.add(new AccountReconciliationResponse(o.toAggregate()));
        }
        return new PaginatedResponse(patients, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public AccountReconciliationDto findById(UUID id) {
        Optional<AccountReconciliation> object = this.repositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }

        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.ACCOUNT_RECONCILIATION_NOT_FOUND, new ErrorField("id", DomainErrorMessage.ACCOUNT_RECONCILIATION_NOT_FOUND.getReasonPhrase())));
    }

}
