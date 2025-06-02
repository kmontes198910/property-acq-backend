package com.kynsof.identity.infrastructure.entities;

import com.kynsof.identity.domain.dto.WalletDto;
import com.kynsof.identity.domain.dto.WalletTransactionDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private BigDecimal balance;

    @OneToOne()
    @JoinColumn(name = "user_system_id", nullable = false)
    private UserSystem userSystem;

    @OneToMany(mappedBy = "wallet",fetch = FetchType.EAGER)
    private Set<WalletTransaction> transactions = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;


    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Wallet(WalletDto dto) {
        this.id = dto.getId();
        this.balance = dto.getBalance();
        this.userSystem = new UserSystem(dto.getCustomer());
     //   this.transactions = dto.getTransactions().stream().map(WalletTransaction::new).collect(Collectors.toSet());
    }
    public WalletDto toAggregate() {
        List<WalletTransactionDto> walletTransactions = transactions != null ?
                transactions.stream().map(WalletTransaction::toAggregate).collect(Collectors.toList()) : new ArrayList<>();
        return new WalletDto(id,balance,userSystem.toAggregate(),walletTransactions );
    }
}