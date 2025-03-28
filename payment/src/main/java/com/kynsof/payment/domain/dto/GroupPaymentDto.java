package com.kynsof.payment.domain.dto;

import com.kynsof.payment.domain.dto.enumDto.GroupPaymentStatus;
import com.kynsof.payment.domain.dto.enumDto.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupPaymentDto {
    private  UUID id;
    private  String requestId;
    private  LocalDateTime paymentDate;
    private  String authorizationCode;
    private  String reference;
    private  String processUrl;
    private  GroupPaymentStatus status;
    private  LocalDateTime createdAt;
    private  double totalAmount;
    private String internalReferenceNumber;
    private ClientDto client;
    private BusinessDto business;
    private PaymentType paymentType;
    private Boolean isReverse;

    public GroupPaymentDto(UUID id, String requestId, LocalDateTime paymentDate, String authorizationCode,
                           String reference, String processUrl, GroupPaymentStatus status,
                           LocalDateTime createdAt, double totalAmount) {
        this.id = id;
        this.requestId = requestId;
        this.paymentDate = paymentDate;
        this.authorizationCode = authorizationCode;
        this.reference = reference;
        this.processUrl = processUrl;
        this.status = status;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
    }
}
