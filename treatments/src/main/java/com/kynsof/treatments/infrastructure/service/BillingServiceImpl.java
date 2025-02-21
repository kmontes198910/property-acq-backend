package com.kynsof.treatments.infrastructure.service;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;

import com.kynsof.treatments.application.query.billing.getbyid.BillingResponse;
import com.kynsof.treatments.domain.dto.BillingDto;
import com.kynsof.treatments.domain.dto.enumDto.BillingStatus;
import com.kynsof.treatments.domain.dto.enumDto.GroupPaymentStatus;
import com.kynsof.treatments.domain.service.IBillingService;
import com.kynsof.treatments.infrastructure.entity.*;
import com.kynsof.treatments.infrastructure.repositories.command.BillingWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.command.GroupPaymentWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.command.PaymentDetailWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.query.BillingReadDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.query.BusinessReadDataJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BillingServiceImpl implements IBillingService {

    private final BillingReadDataJPARepository repositoryQuery;

    private final BillingWriteDataJPARepository repositoryCommand;
    private final GroupPaymentWriteDataJPARepository groupPaymentWriteDataJPARepository;
    private final PaymentDetailWriteDataJPARepository paymentDetailWriteDataJPARepository;
    private final BusinessReadDataJPARepository businessReadDataJPARepository;

    public BillingServiceImpl(BillingReadDataJPARepository repositoryQuery, BillingWriteDataJPARepository repositoryCommand, GroupPaymentWriteDataJPARepository groupPaymentWriteDataJPARepository, PaymentDetailWriteDataJPARepository paymentDetailWriteDataJPARepository, BusinessReadDataJPARepository businessReadDataJPARepository) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
        this.groupPaymentWriteDataJPARepository = groupPaymentWriteDataJPARepository;
        this.paymentDetailWriteDataJPARepository = paymentDetailWriteDataJPARepository;
        this.businessReadDataJPARepository = businessReadDataJPARepository;
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
        GenericSpecificationsBuilder<Procedure> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Billing> data = this.repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
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
    public boolean existsByCodeAndBusinessIdAndStatusAndPatientId(String code, UUID businessId, BillingStatus status, UUID patientId) {
        return this.repositoryQuery.existsByCodeAndBusinessIdAndStatusAndPatientId(code, businessId, status,patientId);
    }
}


