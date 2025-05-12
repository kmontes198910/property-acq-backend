package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankDocumentDto {
    private UUID id;
    private String fileName;
    private String document;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BankAccountDto bankAccount;
}
