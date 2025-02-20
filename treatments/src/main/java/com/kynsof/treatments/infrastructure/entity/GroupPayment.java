package com.kynsof.treatments.infrastructure.entity;

import com.kynsof.treatments.domain.dto.GroupPaymentDto;
import com.kynsof.treatments.domain.dto.enumDto.GroupPaymentStatus;
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

    @Column(nullable = false, unique = true)
    private String requestId;

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    @Column(nullable = false, unique = true)
    private String authorizationCode;

    @Column(nullable = false, unique = true)
    private String reference;

    @Column(nullable = false)
    private String processUrl;
    @Enumerated(EnumType.STRING)
    private GroupPaymentStatus status;

    @OneToMany(mappedBy = "groupPayment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PaymentDetail> paymentDetails;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public GroupPayment(String requestId, LocalDateTime paymentDate, String authorizationCode, String reference, String processUrl, Business business) {
        this.requestId = requestId;
        this.paymentDate = paymentDate;
        this.authorizationCode = authorizationCode;
        this.reference = reference;
        this.processUrl = processUrl;
        this.business = business;
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
        return dto;
    }
}