package com.kynsof.treatments.application.query.groupPayment.getbyid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsof.treatments.domain.dto.GroupPaymentDto;
import com.kynsof.treatments.domain.dto.enumDto.GroupPaymentStatus;
import lombok.Getter;
import lombok.Setter;

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
    private Double totalAmount;


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
    }
}
