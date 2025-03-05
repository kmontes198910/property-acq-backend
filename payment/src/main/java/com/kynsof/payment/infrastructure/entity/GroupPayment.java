package com.kynsof.payment.infrastructure.entity;

import com.kynsof.payment.domain.dto.GroupPaymentDto;
import com.kynsof.payment.domain.dto.enumDto.GroupPaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "group_payment")
@Getter
@Setter
@NoArgsConstructor
public class GroupPayment {

    @Id
    @GeneratedValue
    private UUID id;

    private String requestId;
    private LocalDateTime paymentDate;
    private String authorizationCode;
    private String reference;
    private double totalAmount;
    private String processUrl;

    @Enumerated(EnumType.STRING)
    private GroupPaymentStatus status;

    @OneToMany(mappedBy = "groupPayment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PaymentDetail> paymentDetails;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public GroupPayment(String requestId, LocalDateTime paymentDate, String authorizationCode, String reference,
                        String processUrl, Business business, Client client, Double totalAmount) {
        this.requestId = requestId;
        this.paymentDate = paymentDate;
        this.authorizationCode = authorizationCode;
        this.reference = reference;
        this.processUrl = processUrl;
        this.business = business;
        this.client = client;
        this.totalAmount = totalAmount;
    }

    public GroupPaymentDto toAggregate() {
        GroupPaymentDto dto = new GroupPaymentDto();
        dto.setRequestId(requestId);
        dto.setPaymentDate(paymentDate);
        dto.setAuthorizationCode(authorizationCode);
        dto.setReference(reference);
        dto.setProcessUrl(processUrl);
        dto.setStatus(status);
        dto.setId(id);
        dto.setCreatedAt(createdAt);
        dto.setTotalAmount(totalAmount);
        dto.setClient(client.toAggregate());
        dto.setBusiness(business.toAggregate());
        return dto;
    }
}