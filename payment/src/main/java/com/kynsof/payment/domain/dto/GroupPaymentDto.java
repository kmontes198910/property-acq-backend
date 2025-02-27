package com.kynsof.payment.domain.dto;

import com.kynsof.payment.domain.dto.enumDto.GroupPaymentStatus;
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
    private UUID id;
    private String requestId;
    private LocalDateTime paymentDate;
    private String authorizationCode;
    private String reference;
    private String processUrl;
    private GroupPaymentStatus status;
    private LocalDateTime createdAt;
    private double totalAmount;
}
