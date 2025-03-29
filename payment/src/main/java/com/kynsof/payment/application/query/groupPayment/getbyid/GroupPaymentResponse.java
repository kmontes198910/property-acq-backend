package com.kynsof.payment.application.query.groupPayment.getbyid;

import com.kynsof.payment.domain.dto.BusinessDto;
import com.kynsof.payment.domain.dto.ClientDto;
import com.kynsof.payment.domain.dto.GroupPaymentDto;
import com.kynsof.payment.domain.dto.enumDto.GroupPaymentStatus;
import com.kynsof.payment.domain.dto.enumDto.PaymentType;
import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class GroupPaymentResponse implements IResponse {
    private UUID id;
    private String requestId;
    private LocalDateTime paymentDate;
    private String authorizationCode;
    private String reference;
    private String processUrl;
    private GroupPaymentStatus status;
    private LocalDateTime createdAt;
    private BigDecimal totalAmount;
    private ClientDto client;
    private BusinessDto business;
    private String internalReferenceNumber;
    private PaymentType paymentType;
    private Boolean isReverse;

    public GroupPaymentResponse(GroupPaymentDto aggregate) {
        this.id = aggregate.getId();
        this.requestId = aggregate.getRequestId();
        this.paymentDate = aggregate.getPaymentDate();
        this.authorizationCode = aggregate.getAuthorizationCode();
        this.reference = aggregate.getReference();
        this.processUrl = aggregate.getProcessUrl();
        this.status = aggregate.getStatus();
        this.createdAt = aggregate.getCreatedAt();
        this.totalAmount = aggregate.getTotalAmount();
        this.client = aggregate.getClient();
        this.business = aggregate.getBusiness();
        this.internalReferenceNumber = aggregate.getInternalReferenceNumber();
        this.paymentType = aggregate.getPaymentType();
        this.isReverse = aggregate.getIsReverse();
    }
}
