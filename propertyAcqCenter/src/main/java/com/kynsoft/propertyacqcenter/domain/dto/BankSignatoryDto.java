package com.kynsoft.propertyacqcenter.domain.dto;

import com.kynsoft.propertyacqcenter.domain.enums.SignatureAuthority;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankSignatoryDto {
    private UUID id;
    private BankAccountDto bankAccount;
    private String fullName;
    private String title;
    private String phone;
    private String email;
    private SignatureAuthority authorityLevel;
}
