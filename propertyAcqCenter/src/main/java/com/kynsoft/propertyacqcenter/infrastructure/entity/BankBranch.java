package com.kynsoft.propertyacqcenter.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankBranch {
    @Column(name = "branch_name")
    private String name;
    
    @Embedded
    private EmbeddableAddress address;
    
    @Column(name = "branch_phone")
    private String phone;
}
