package com.kynsof.identity.infrastructure.services;

import com.kynsof.identity.application.query.paymentdev.getbyid.PaymentDevResponse;
import com.kynsof.identity.domain.dto.PaymentDevDto;
import com.kynsof.identity.domain.interfaces.service.IPaymentDevService;
import com.kynsof.identity.infrastructure.entities.PaymentDev;
import com.kynsof.identity.infrastructure.repository.command.PaymentDevWriteDataJPARepository;
import com.kynsof.identity.infrastructure.repository.query.PaymentDevReadDataJPARepository;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentDevServiceImpl implements IPaymentDevService {

    @Autowired
    private PaymentDevWriteDataJPARepository repositoryCommand;

    @Autowired
    private PaymentDevReadDataJPARepository repositoryQuery;

    @Override
    public void create(PaymentDevDto paymentDevDto) {
        this.repositoryCommand.save(new PaymentDev(paymentDevDto));
    }

    @Override
    public void update(PaymentDevDto paymentDevDto) {
        this.repositoryCommand.save(new PaymentDev(paymentDevDto));
    }

    @Override
    public void delete(PaymentDevDto paymentDevDto) {
        PaymentDev delete = new PaymentDev(paymentDevDto);

        delete.setReference(delete.getReference() + " + " + UUID.randomUUID());

        this.repositoryCommand.save(delete);
    }

    @Override
    public PaymentDevDto findById(UUID id) {
        Optional<PaymentDev> object = this.repositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }
        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.MODULE_NOT_FOUND, new ErrorField("id", "PaymentDev not found.")));
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<PaymentDevResponse> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<PaymentDev> data = this.repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<PaymentDev> data) {
        List<PaymentDevResponse> moduleListResponses = new ArrayList<>();
        for (PaymentDev o : data.getContent()) {
            moduleListResponses.add(new PaymentDevResponse(o.toAggregate()));
        }
        return new PaginatedResponse(moduleListResponses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public Long countByReferenceAndNotId(String name, UUID id) {
        return this.repositoryQuery.countByReferenceAndNotId(name, id);
    }
}
