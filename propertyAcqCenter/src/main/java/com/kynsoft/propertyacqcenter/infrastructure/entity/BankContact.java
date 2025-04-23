package com.kynsoft.propertyacqcenter.infrastructure.entity;

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
}
