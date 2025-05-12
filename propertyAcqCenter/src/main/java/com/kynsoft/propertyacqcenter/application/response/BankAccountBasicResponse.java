package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountBasicResponse implements IResponse {
    private UUID id;
    private String bankName;//

    public BankAccountBasicResponse(BankAccountDto dto) {
        this.id = dto.getId();
        this.bankName = dto.getBankName();
    }

}
