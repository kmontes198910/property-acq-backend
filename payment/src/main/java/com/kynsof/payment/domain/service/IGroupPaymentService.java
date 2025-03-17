package com.kynsof.payment.domain.service;

import com.kynsof.payment.application.command.groupPayment.createGroupPaymentUnif.CreateBillingPartialRequest;
import com.kynsof.payment.domain.dto.GroupPaymentDto;
import com.kynsof.payment.domain.dto.enumDto.GroupPaymentStatus;
import com.kynsof.payment.domain.dto.enumDto.PaymentType;
import com.kynsof.payment.domain.dto.enumDto.TypeOperation;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IGroupPaymentService {
    void delete(UUID id);
    UUID createGroupPayment(List<UUID> billingIds, UUID businessId, UUID patientsId);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    GroupPaymentDto findById(UUID id);
    PaginatedResponse searchPaymentDetail(Pageable pageable, List<FilterCriteria> filterCriteria);

    void update(UUID id, String reference, String authorizationCode, String requestId, String processUrl, GroupPaymentStatus status);

    void updateAdminSystems(UUID id, String reference, String authorizationCode, PaymentType paymentType, GroupPaymentStatus status);

    UUID createBillingsAndGroupPayment(UUID clientId, UUID businessId, List<CreateBillingPartialRequest> billings, UUID userSystemId, String userSystemFullName, PaymentType paymentType, GroupPaymentStatus paymentStatus, String insuranceId, TypeOperation typeOperation, boolean proforma, String authorizationCode, String reference);
}