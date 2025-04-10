package com.kynsof.payment.application.service;

import com.kynsof.payment.application.command.groupPayment.createGroupPaymentUnif.CreateBillingPartialRequest;
import com.kynsof.payment.application.query.groupPayment.getbyid.GroupPaymentResponse;
import com.kynsof.payment.application.query.groupPaymentDetails.SearchGroupPaymentDetailResponse;
import com.kynsof.payment.domain.dto.BillingDto;
import com.kynsof.payment.domain.dto.GroupPaymentDto;
import com.kynsof.payment.domain.dto.enumDto.*;
import com.kynsof.payment.domain.service.IGroupPaymentService;
import com.kynsof.payment.infrastructure.entity.*;
import com.kynsof.payment.infrastructure.repositories.command.BillingWriteDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.command.ClientWriteDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.command.GroupPaymentWriteDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.command.PaymentDetailWriteDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.query.*;
import com.kynsof.payment.application.service.http.PatientHttpUUIDService;
import com.kynsof.share.core.application.payment.domain.placeToPlay.PaymentServiceStatusResponse;
import com.kynsof.share.core.application.payment.infrastructure.service.config.PaymentServiceClient;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.http.entity.PatientHttp;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

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
    private final PatientHttpUUIDService patientHttpUUIDService;
    private final ClientWriteDataJPARepository clientWriteDataJPARepository;
    private final PaymentServiceClient paymentServiceClient;

    public GroupPaymentServiceImpl(BillingReadDataJPARepository repositoryQuery,
                                   BillingWriteDataJPARepository repositoryCommand,
                                   GroupPaymentWriteDataJPARepository groupPaymentWriteDataJPARepository,
                                   PaymentDetailWriteDataJPARepository paymentDetailWriteDataJPARepository,
                                   GroupPaymentDetailReadDataJPARepository paymentDetailReadDataJPARepository,
                                   GroupPaymentReadDataJPARepository groupPaymentReadDataJPARepository,
                                   BusinessReadDataJPARepository businessReadDataJPARepository,
                                   ClientReadDataJPARepository clientReadDataJPARepository, PatientHttpUUIDService patientHttpUUIDService, ClientWriteDataJPARepository clientWriteDataJPARepository, PaymentServiceClient paymentServiceClient) {
        this.repositoryQuery = repositoryQuery;
        this.billingWriteDataJPARepository = repositoryCommand;
        this.groupPaymentWriteDataJPARepository = groupPaymentWriteDataJPARepository;
        this.paymentDetailWriteDataJPARepository = paymentDetailWriteDataJPARepository;
        this.paymentDetailReadDataJPARepository = paymentDetailReadDataJPARepository;
        this.groupPaymentReadDataJPARepository = groupPaymentReadDataJPARepository;
        this.businessReadDataJPARepository = businessReadDataJPARepository;
        this.clientReadDataJPARepository = clientReadDataJPARepository;
        this.patientHttpUUIDService = patientHttpUUIDService;
        this.clientWriteDataJPARepository = clientWriteDataJPARepository;
        this.paymentServiceClient = paymentServiceClient;
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
        paymentDetailWriteDataJPARepository.deleteAll(paymentDetails);

        groupPaymentWriteDataJPARepository.delete(groupPayment);
    }

    @Override
//    @Transactional
    public UUID createGroupPayment(List<UUID> billingIds, UUID businessId, UUID patientsId) {
        List<Billing> billings = this.repositoryQuery.findAllById(billingIds);
        Business business = this.businessReadDataJPARepository.findById(businessId).orElseThrow();
        Client patients = this.clientReadDataJPARepository.findById(patientsId).orElseThrow();

        BigDecimal totalAmount = billings.stream()
                .map(b -> BigDecimal.valueOf(b.getCost()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);// Suma todos los costos
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
        filterCriteria(filterCriteria);
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

    private void filterCriteria(List<FilterCriteria> filterCriteria) {
        filterCriteria.forEach(filter -> {
            if ("status".equals(filter.getKey()) && filter.getValue() instanceof String) {
                filter.setValue(parseEnum(GroupPaymentStatus.class, (String) filter.getValue(), "GroupPaymentStatus"));
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

    @Override
    public void update(UUID id, String reference, String authorizationCode, String requestId, String processUrl, GroupPaymentStatus status) throws IOException {
        GroupPayment groupPayment = this.groupPaymentReadDataJPARepository.findById(id).orElseThrow();
        groupPayment.setStatus(status);
        groupPayment.setReference(reference);
        groupPayment.setAuthorizationCode(authorizationCode);
        groupPayment.setRequestId(requestId);
        groupPayment.setProcessUrl(processUrl);
        groupPayment.setPaymentType(PaymentType.PLACETOPAY);
        if (status == GroupPaymentStatus.PAYMENT_APPROVED) {
            PaymentServiceStatusResponse serviceStatusResponse = paymentServiceClient.validateStatusPayment(groupPayment.getRequestId(), groupPayment.getBusiness().getId());
            if (serviceStatusResponse.getStatus().equals("APPROVED")) {
                if (authorizationCode.isEmpty()) {
                    throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.PAYMENT_NOT_FOUND, new ErrorField("autorization", "El codigo de autorizacion no puede ser vacio")));
                }
                groupPayment.setPaymentDate(LocalDateTime.now());
                groupPayment.setAuthorizationCode(authorizationCode);
            }
        }
        this.groupPaymentWriteDataJPARepository.save(groupPayment);

        updateBilling(status, groupPayment);
    }


    @Override
    public void updateAdminSystems(UUID id, String reference, String authorizationCode,
                                   PaymentType paymentType, GroupPaymentStatus status, String requestId) {
        GroupPayment groupPayment = this.groupPaymentReadDataJPARepository.findById(id).orElseThrow();
        groupPayment.setStatus(status);
        groupPayment.setReference(reference);
        groupPayment.setAuthorizationCode(authorizationCode);
        groupPayment.setPaymentType(paymentType);
        groupPayment.setStatus(status);
        groupPayment.setRequestId(requestId);
        if (status == GroupPaymentStatus.PAYMENT_APPROVED) {
            if (authorizationCode.isEmpty()) {
                throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.PAYMENT_NOT_FOUND, new ErrorField("autorization", "El codigo de autorizacion no puede ser vacio")));
            }
            groupPayment.setPaymentDate(LocalDateTime.now());
        }
        this.groupPaymentWriteDataJPARepository.save(groupPayment);
        updateBilling(status, groupPayment);
    }

    private void updateBilling(GroupPaymentStatus status, GroupPayment groupPayment) {
        if (status == GroupPaymentStatus.PAYMENT_APPROVED) {
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
                                              String reference, String requestId) {
        if (requestId != null && requestId.length() > 3) {
            Optional<GroupPayment> groupPayment = this.groupPaymentReadDataJPARepository.findLastByRequestId(requestId);
            if (groupPayment.isPresent()) {
                return groupPayment.get().getId();
            }
        }
        System.err.println("Entro aqui antes de leer el cliente");
        Client client;
        try {
            client = this.clientReadDataJPARepository.findById(clientId)
                    .orElseThrow(() -> new NoSuchElementException("Client not found with ID: " + clientId));
        } catch (NoSuchElementException e) {
            System.err.println("Client not found with ID: " + clientId);
            PatientHttp patient = patientHttpUUIDService.sendGetHttpRequest(clientId);
            client = new Client(
                    patient.getId(),
                    patient.getIdentification(),
                    patient.getName(),
                    patient.getLastName(),
                    Status.valueOf(patient.getStatus()),
                    patient.getEmail(),
                    patient.getPhone()
            );
            this.clientWriteDataJPARepository.save(client);
        }
        System.err.println("Entro aqui despues de leer el cliente");
        Business business = this.businessReadDataJPARepository.findById(businessId).orElseThrow();
        System.err.println("Entro aqui despues de leer la empresa");
        BillingStatus billingStatus;

        if (paymentStatus.equals(GroupPaymentStatus.PAYMENT_CASH) || paymentStatus.equals(GroupPaymentStatus.PAYMENT_APPROVED)) {
            billingStatus = BillingStatus.PAID;
        } else {
            billingStatus = BillingStatus.PENDING;
        }
        Client finalClient = client;
        List<BillingDto> billingDtos = billings.stream().map(billing ->
                new BillingDto(
                        UUID.randomUUID(),
                        finalClient.toAggregate(),
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
        System.err.println("Construye los billing");
        List<Billing> billingList = billingWriteDataJPARepository.saveAll(billingDtos.stream().map(Billing::new).toList());
        System.err.println("Salva los billing");

        List<UUID> billingIds = billingList.stream()
                .map(Billing::getId)
                .toList();

        UUID groupPaymentId = createGroupPayment(
                billingIds,
                businessId,
                clientId
        );
        System.err.println("Salva los group payment");
        updateAdminSystems(
                groupPaymentId,
                reference,
                authorizationCode,
                paymentType,
                paymentStatus,
                requestId
        );
        System.err.println("Actualiza los group payment");
        return groupPaymentId;
    }

    @Override
    public List<GroupPayment> findByStatus(GroupPaymentStatus groupPaymentStatus) {
        return this.groupPaymentReadDataJPARepository.findByStatus(groupPaymentStatus);
    }

    @Override
    public void reverse(UUID id) {
        GroupPayment groupPayment = this.groupPaymentReadDataJPARepository.findById(id).orElseThrow();
        if (!groupPayment.canBeRefunded()) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.PAYMENT_NOT_REVERSE, new ErrorField("paymentDate", "El pago solo puede ser reversado antes de las 12:00 PM del mismo día.")));
        }
        try {
            PaymentServiceStatusResponse serviceStatusResponse = paymentServiceClient.
                    reverseTransaction(groupPayment.getBusiness().getId(), groupPayment.getReference());

            groupPayment.setStatus(GroupPaymentStatus.REVERSE);
            groupPaymentWriteDataJPARepository.save(groupPayment);

        } catch (IOException e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.PAYMENT_NOT_PRESENT, new ErrorField("id", "No se puede reversar la transacción.")));
        }
    }

    @Override
    public void changeStatusByNotification(String requestId) {
        GroupPayment groupPayment = this.groupPaymentReadDataJPARepository.findLastByRequestId(requestId).orElseThrow();
        PaymentServiceStatusResponse serviceStatusResponse = null;
        try {
            serviceStatusResponse = paymentServiceClient.validateStatusPayment(groupPayment.getRequestId(), groupPayment.getBusiness().getId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (serviceStatusResponse.getStatus().equals("APPROVED")) {
            groupPayment.setPaymentDate(LocalDateTime.now());
            groupPayment.setAuthorizationCode(serviceStatusResponse.getAuthorization());
            groupPayment.setStatus(GroupPaymentStatus.PAYMENT_APPROVED);
        }
        if (serviceStatusResponse.getStatus().equals("REJECTED")) {
            groupPayment.setPaymentDate(LocalDateTime.now());
            groupPayment.setAuthorizationCode(serviceStatusResponse.getAuthorization());
            groupPayment.setStatus(GroupPaymentStatus.REJECTED);
        }
        groupPaymentWriteDataJPARepository.save(groupPayment);

    }


    @Override
    public GroupPaymentDto findById(UUID id) {
        try {
            return this.groupPaymentReadDataJPARepository.findById(id).get().toAggregate();
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.PAYMENT_NOT_PRESENT, new ErrorField("id", "Group Payment not found.")));
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
