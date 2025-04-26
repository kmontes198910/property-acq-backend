package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.embedded.BankContactDto;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
public class BankContact {
    @Column(name = "contact_name")
    private String name;

    @Column(name = "contact_phone")
    private String phone;

    @Column(name = "contact_email")
    private String email;

    public BankContact(BankContactDto dto) {
        this.name = dto.getName();
        this.phone = dto.getPhone();
        this.email = dto.getEmail();
    }

}
