package com.kynsof.payment.infrastructure.service;

import com.kynsof.payment.application.query.billing.getbyid.BillingResponse;
import com.kynsof.payment.domain.dto.BillingDto;
import com.kynsof.payment.domain.dto.enumDto.BillingStatus;
import com.kynsof.payment.domain.service.IBillingService;
import com.kynsof.payment.infrastructure.entity.Billing;
import com.kynsof.payment.infrastructure.repositories.command.BillingWriteDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.command.GroupPaymentWriteDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.command.PaymentDetailWriteDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.query.BillingReadDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.query.BusinessReadDataJPARepository;
import com.kynsof.share.core.domain.EUserType;
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
public class BillingServiceImpl implements IBillingService {

    private final BillingReadDataJPARepository repositoryQuery;

    private final BillingWriteDataJPARepository repositoryCommand;

    public BillingServiceImpl(BillingReadDataJPARepository repositoryQuery, BillingWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public void create(BillingDto dto) {
        this.repositoryCommand.save(new Billing(dto));
    }

    @Override
    @Transactional
    public void createAll(List<BillingDto> dtoList) {
        List<Billing> billings = new ArrayList<>();
        for (BillingDto dto : dtoList) {
            billings.add(new Billing(dto));
        }
        this.repositoryCommand.saveAll(billings);
    }

    @Override
    @Transactional
    public void update(BillingDto billingDto) {
        Billing billing = this.repositoryQuery.findById(billingDto.getId()).orElseThrow();
        billing.setCode(billingDto.getCode());
        billing.setDescription(billingDto.getDescription());
        billing.setCost(billingDto.getCost());
        billing.setStatus(billingDto.getStatus());
        billing.setProforma(billingDto.isProforma());
        this.repositoryCommand.save(billing);
    }

    @Override
    public void delete(BillingDto object) {
        try {
            this.repositoryCommand.deleteById(object.getId());
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        filterCriteria(filterCriteria);
        GenericSpecificationsBuilder<Billing> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Billing> data = this.repositoryQuery.findAll(specifications, pageable);
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

    private PaginatedResponse getPaginatedResponse(Page<Billing> data) {
        List<BillingResponse> patients = new ArrayList<>();
        for (Billing o : data.getContent()) {
            patients.add(new BillingResponse(o.toAggregate()));
        }
        return new PaginatedResponse(patients, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public BillingDto findById(UUID id) {
        Optional<Billing> object = this.repositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }

        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.MEDICINES_NOT_FOUND, new ErrorField("id", "Medicione not found.")));
    }

//    @Override
//    @Transactional
//    public UUID createGroupPayment(List<UUID> billingIds) {
//        // Recuperar las facturas seleccionadas
//        List<Billing> billings = this.repositoryQuery.findAllById(billingIds);
//        Business business = this.businessReadDataJPARepository.findById(billings.get(0).getId()).orElseThrow();
//        // Crear el pago agrupado
//        GroupPayment groupPayment = new GroupPayment("", LocalDateTime.now(), "",
//                "", "", business);
//        groupPayment.setStatus(GroupPaymentStatus.PENDING_PAID);
//        groupPayment = this.groupPaymentWriteDataJPARepository.save(groupPayment);
//
//        // Crear detalles del pago
//        for (Billing billing : billings) {
//            PaymentDetail paymentDetail = new PaymentDetail(groupPayment, billing, billing.getCost());
//            this.paymentDetailWriteDataJPARepository.save(paymentDetail);
//
//            // Marcar la factura como pagada
//            billing.setStatus(BillingStatus.PENDING_PAID);
//            this.repositoryCommand.save(billing);
//        }
//
//        return groupPayment.getId();
//    }
    @Override
    public boolean existsByCodeAndBusinessIdAndStatusAndPatientId(String code, UUID businessId, BillingStatus status, UUID clientId) {
        return this.repositoryQuery.existsByCodeAndBusinessIdAndStatusAndClientId(code, businessId, status, clientId);
    }
}
