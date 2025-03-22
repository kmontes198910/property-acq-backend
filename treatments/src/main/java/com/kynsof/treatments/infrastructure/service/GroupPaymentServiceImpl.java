package com.kynsof.treatments.infrastructure.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsof.treatments.application.query.groupPayment.getbyid.GroupPaymentResponse;
import com.kynsof.treatments.application.query.groupPaymentDetails.SearchGroupPaymentDetailResponse;
import com.kynsof.treatments.domain.dto.GroupPaymentDto;
import com.kynsof.treatments.domain.dto.enumDto.BillingStatus;
import com.kynsof.treatments.domain.dto.enumDto.GroupPaymentStatus;
import com.kynsof.treatments.domain.service.IGroupPaymentService;
import com.kynsof.treatments.infrastructure.entity.*;
import com.kynsof.treatments.infrastructure.repositories.command.BillingWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.command.GroupPaymentWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.command.PaymentDetailWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.query.*;
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
    private final PatientsReadDataJPARepository patientsReadDataJPARepository;

    public GroupPaymentServiceImpl(BillingReadDataJPARepository repositoryQuery, BillingWriteDataJPARepository repositoryCommand, GroupPaymentWriteDataJPARepository groupPaymentWriteDataJPARepository, PaymentDetailWriteDataJPARepository paymentDetailWriteDataJPARepository, GroupPaymentDetailReadDataJPARepository paymentDetailReadDataJPARepository, GroupPaymentReadDataJPARepository groupPaymentReadDataJPARepository, BusinessReadDataJPARepository businessReadDataJPARepository, PatientsReadDataJPARepository patientsReadDataJPARepository) {
        this.repositoryQuery = repositoryQuery;
        this.billingWriteDataJPARepository = repositoryCommand;
        this.groupPaymentWriteDataJPARepository = groupPaymentWriteDataJPARepository;
        this.paymentDetailWriteDataJPARepository = paymentDetailWriteDataJPARepository;
        this.paymentDetailReadDataJPARepository = paymentDetailReadDataJPARepository;
        this.groupPaymentReadDataJPARepository = groupPaymentReadDataJPARepository;
        this.businessReadDataJPARepository = businessReadDataJPARepository;
        this.patientsReadDataJPARepository = patientsReadDataJPARepository;
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
        Patients patients = this.patientsReadDataJPARepository.findById(patientsId).orElseThrow();

        Double totalAmount = billings.stream()
                .map(Billing::getCost)  // Extrae el costo de cada billing
                .reduce(0.0, Double::sum); // Suma todos los costos
        GroupPayment groupPayment = new GroupPayment("",null, "",
                "", "", business, patients,totalAmount);
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
        this.groupPaymentWriteDataJPARepository.save(groupPayment);
    }

    @Override
    public GroupPaymentDto findById(UUID id) {
        return this.groupPaymentReadDataJPARepository.findById(id).get().toAggregate();
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