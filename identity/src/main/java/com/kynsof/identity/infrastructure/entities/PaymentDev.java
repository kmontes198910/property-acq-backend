package com.kynsof.identity.infrastructure.entities;

import com.kynsof.identity.domain.dto.PaymentDevDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentDev {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserSystem user;

    private Double payment;
    private String reference;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;


    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public PaymentDev(PaymentDevDto dto) {
        this.id = dto.getId();
        this.user = dto.getUser() != null ? new UserSystem(dto.getUser()) : null;
        this.payment = dto.getPayment();
        this.reference = dto.getReference();
    }
    
    public PaymentDevDto toAggregate() {
        return new PaymentDevDto(this.id, this.user.toAggregate(), this.payment, this.reference, this.createdAt);
    }

}
