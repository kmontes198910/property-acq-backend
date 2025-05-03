package com.kynsoft.propertyacqcenter.infrastructure.entity.embeddedEntity;

import com.kynsoft.propertyacqcenter.domain.dto.embedded.BankBranchDto;
import com.kynsoft.propertyacqcenter.infrastructure.entity.EmbeddableAddress;
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

    public BankBranch(BankBranchDto dto) {
        this.name = dto.getName();
        this.address = new EmbeddableAddress(dto.getAddress());
        this.phone = dto.getPhone();
    }

}
