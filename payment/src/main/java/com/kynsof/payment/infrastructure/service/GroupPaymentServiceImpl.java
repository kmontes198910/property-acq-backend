package com.kynsof.payment.infrastructure.service;

import com.kynsof.payment.application.command.groupPayment.createGroupPaymentUnif.CreateBillingPartialRequest;
import com.kynsof.payment.application.query.groupPayment.getbyid.GroupPaymentResponse;
import com.kynsof.payment.application.query.groupPaymentDetails.SearchGroupPaymentDetailResponse;
import com.kynsof.payment.domain.dto.BillingDto;
import com.kynsof.payment.domain.dto.GroupPaymentDto;
import com.kynsof.payment.domain.dto.enumDto.BillingStatus;
import com.kynsof.payment.domain.dto.enumDto.GroupPaymentStatus;
import com.kynsof.payment.domain.dto.enumDto.PaymentType;
import com.kynsof.payment.domain.dto.enumDto.TypeOperation;
import com.kynsof.payment.domain.service.IGroupPaymentService;
import com.kynsof.payment.infrastructure.entity.Billing;
import com.kynsof.payment.infrastructure.entity.Business;
import com.kynsof.payment.infrastructure.entity.Client;
import com.kynsof.payment.infrastructure.entity.GroupPayment;
import com.kynsof.payment.infrastructure.entity.PaymentDetail;
import com.kynsof.payment.infrastructure.repositories.command.BillingWriteDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.command.GroupPaymentWriteDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.command.PaymentDetailWriteDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.query.BillingReadDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.query.BusinessReadDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.query.ClientReadDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.query.GroupPaymentDetailReadDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.query.GroupPaymentReadDataJPARepository;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GroupPaymentServiceImpl implements IGroupPaymentService {

    private final BillingReadDataJPARepository repositoryQuery;

    private final BillingWriteDataJPARepository billingWriteDataJPARepository;
    private final GroupPaymentWriteDataJPARepository groupPaymentWriteDataJPARepository;
    private final PaymentDetailWriteDataJPARepository paymentDetailWriteDataJPARepository;
    private final GroupPaymentDetailReadDataJPARepository paymentDetailReadDataJPARepository;
    private final GroupPaymentReadDataJPARepository groupPaymentReadDataJPARepository;
    private final BusinessReadDataJPARepository businessReadDataJPARepository;
    private final ClientReadDataJPARepository clientReadDataJPARepository;

    public GroupPaymentServiceImpl(BillingReadDataJPARepository repositoryQuery, 
                                   BillingWriteDataJPARepository repositoryCommand, 
                                   GroupPaymentWriteDataJPARepository groupPaymentWriteDataJPARepository, 
                                   PaymentDetailWriteDataJPARepository paymentDetailWriteDataJPARepository, 
                                   GroupPaymentDetailReadDataJPARepository paymentDetailReadDataJPARepository, 
                                   GroupPaymentReadDataJPARepository groupPaymentReadDataJPARepository, 
                                   BusinessReadDataJPARepository businessReadDataJPARepository, 
                                   ClientReadDataJPARepository clientReadDataJPARepository) {
        this.repositoryQuery = repositoryQuery;
        this.billingWriteDataJPARepository = repositoryCommand;
        this.groupPaymentWriteDataJPARepository = groupPaymentWriteDataJPARepository;
        this.paymentDetailWriteDataJPARepository = paymentDetailWriteDataJPARepository;
        this.paymentDetailReadDataJPARepository = paymentDetailReadDataJPARepository;
        this.groupPaymentReadDataJPARepository = groupPaymentReadDataJPARepository;
        this.businessReadDataJPARepository = businessReadDataJPARepository;
        this.clientReadDataJPARepository = clientReadDataJPARepository;
    }

    @Transactional
    @Override
    public void delete(UUID groupPaymentId) {
        // Buscar el grupo de pago
        GroupPayment groupPayment = groupPaymentWriteDataJPARepository.findById(groupPaymentId)
                .orElseThrow(() -> new IllegalArgumentException("GroupPayment not found with id: " + groupPaymentId));

        // Obtener los detalles del pago asociados al grupo
        List<PaymentDetail> paymentDetails = paymentDetailReadDataJPARepository.findByGroupPayment(groupPayment);

        // Cambiar el estado de las facturas asociadas
        for (PaymentDetail paymentDetail : paymentDetails) {
            Billing billing = paymentDetail.getBilling();
            billing.setStatus(BillingStatus.PENDING); // Cambiar el estado a PENDING u otro apropiado
            billingWriteDataJPARepository.save(billing); // Guardar el cambio
        }

        // Eliminar los detalles del pago asociados
        paymentDetailWriteDataJPARepository.deleteAll(paymentDetails);

        // Eliminar el grupo de pago
        groupPaymentWriteDataJPARepository.delete(groupPayment);
    }

    @Override
//    @Transactional
    public UUID createGroupPayment(List<UUID> billingIds, UUID businessId, UUID patientsId) {
        List<Billing> billings = this.repositoryQuery.findAllById(billingIds);
        Business business = this.businessReadDataJPARepository.findById(businessId).orElseThrow();
        Client patients = this.clientReadDataJPARepository.findById(patientsId).orElseThrow();

        Double totalAmount = billings.stream()
                .map(Billing::getCost) // Extrae el costo de cada billing
                .reduce(0.0, Double::sum); // Suma todos los costos
        GroupPayment groupPayment = new GroupPayment(
                "", 
                null, 
                "",
                "", 
                "", 
                business, 
                patients, 
                totalAmount,
                PaymentType.NONE
        );
        groupPayment.setStatus(GroupPaymentStatus.PENDING_PAID);
        groupPayment = this.groupPaymentWriteDataJPARepository.save(groupPayment);

        for (Billing billing : billings) {
            PaymentDetail paymentDetail = new PaymentDetail(groupPayment, billing, billing.getCost());
            this.paymentDetailWriteDataJPARepository.save(paymentDetail);

            billing.setStatus(BillingStatus.PENDING_PAID);
            this.billingWriteDataJPARepository.save(billing);
        }

        return groupPayment.getId();
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<PaymentDetail> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<GroupPayment> data = this.groupPaymentReadDataJPARepository.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    @Override
    public PaginatedResponse searchPaymentDetail(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<PaymentDetail> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<PaymentDetail> data = this.paymentDetailReadDataJPARepository.findAll(specifications, pageable);
        return getPaginatedResponsePaymentDetail(data);
    }

    @Override
    public void update(UUID id, String reference, String authorizationCode, String requestId, String processUrl, GroupPaymentStatus status) {
        GroupPayment groupPayment = this.groupPaymentReadDataJPARepository.findById(id).orElseThrow();
        groupPayment.setStatus(status);
        groupPayment.setReference(reference);
        groupPayment.setAuthorizationCode(authorizationCode);
        groupPayment.setRequestId(requestId);
        groupPayment.setProcessUrl(processUrl);
        groupPayment.setStatus(status);
        groupPayment.setPaymentType(PaymentType.PLACETOPAY);
        this.groupPaymentWriteDataJPARepository.save(groupPayment);

        updateBilling(status, groupPayment);
    }


    @Override
    public void updateAdminSystems(UUID id, String reference, String authorizationCode,
                                   PaymentType paymentType, GroupPaymentStatus status) {
        GroupPayment groupPayment = this.groupPaymentReadDataJPARepository.findById(id).orElseThrow();
        groupPayment.setStatus(status);
        groupPayment.setReference(reference);
        groupPayment.setAuthorizationCode(authorizationCode);
        groupPayment.setPaymentType(paymentType);
        groupPayment.setStatus(status);
        this.groupPaymentWriteDataJPARepository.save(groupPayment);
        updateBilling(status, groupPayment);
    }
    private void updateBilling(GroupPaymentStatus status, GroupPayment groupPayment) {
        if(status == GroupPaymentStatus.PAYMENT_APPROVED) {
            List<PaymentDetail> paymentDetails = paymentDetailReadDataJPARepository.findByGroupPayment(groupPayment);
            for (PaymentDetail paymentDetail : paymentDetails) {
                Billing billing = paymentDetail.getBilling();
                billing.setStatus(BillingStatus.PAID);
                billingWriteDataJPARepository.save(billing);
            }
        }
    }

    @Override
    public UUID createBillingsAndGroupPayment(UUID clientId, UUID businessId, List<CreateBillingPartialRequest> billings,
                                              UUID userSystemId, String userSystemFullName, PaymentType paymentType,
                                              GroupPaymentStatus paymentStatus, String insuranceId,
                                              TypeOperation typeOperation, boolean proforma, String authorizationCode,
                                              String reference) {
        Client client = this.clientReadDataJPARepository.findById(clientId).orElseThrow();
        Business business = this.businessReadDataJPARepository.findById(businessId).orElseThrow();
        BillingStatus billingStatus;

        if(paymentStatus.equals(GroupPaymentStatus.PAYMENT_CASH) || paymentStatus.equals(GroupPaymentStatus.PAYMENT_APPROVED)) {
            billingStatus = BillingStatus.PAID;
        } else {
            billingStatus = BillingStatus.PENDING;
        }
        List<BillingDto> billingDtos = billings.stream().map(billing ->
                new BillingDto(
                        UUID.randomUUID(),
                        client.toAggregate(),
                        business.toAggregate(),
                        billing.getCode(),
                        billing.getDescription(),
                        billingStatus,
                        proforma,
                        billing.getCost(),
                        userSystemId,
                        userSystemFullName,
                        typeOperation
                )
        ).toList();

        billingWriteDataJPARepository.saveAll(billingDtos.stream().map(Billing::new).toList());

        List<UUID> billingIds = billingDtos.stream()
                .map(BillingDto::getId)
                .toList();

        UUID groupPaymentId = createGroupPayment(
                billingIds,
                businessId,
                clientId
        );

        updateAdminSystems(
                groupPaymentId,
                authorizationCode,
                reference,
                paymentType,
                paymentStatus
        );

        return groupPaymentId;
    }


    @Override
    public GroupPaymentDto findById(UUID id) {
        try {
            return this.groupPaymentReadDataJPARepository.findById(id).get().toAggregate();
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.MEDICINES_NOT_FOUND, new ErrorField("id", "Group Payment not found.")));
        }
    }

    private PaginatedResponse getPaginatedResponse(Page<GroupPayment> data) {
        List<GroupPaymentResponse> groupPaymentResponses = new ArrayList<>();
        for (GroupPayment s : data.getContent()) {
            groupPaymentResponses.add(new GroupPaymentResponse(s.toAggregate()));
        }
        return new PaginatedResponse(groupPaymentResponses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    private PaginatedResponse getPaginatedResponsePaymentDetail(Page<PaymentDetail> data) {
        List<SearchGroupPaymentDetailResponse> groupPaymentResponses = new ArrayList<>();
        for (PaymentDetail s : data.getContent()) {
            groupPaymentResponses.add(new SearchGroupPaymentDetailResponse(s.toAggregate()));
        }
        return new PaginatedResponse(groupPaymentResponses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
