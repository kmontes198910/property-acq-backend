package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.adquisitionProperty.AdquisitionTitleCompanyDto;
import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TitleCompanyAdquisitionPropertyResponse implements IResponse {

    private LocalDate requestForEstoppelLetter;
    private AdquisitionDocumentResponse earnestMoneyDepositConfirmation;

    public TitleCompanyAdquisitionPropertyResponse(AdquisitionTitleCompanyDto dto) {
        this.requestForEstoppelLetter = dto.getRequestForEstoppelLetter();

        this.earnestMoneyDepositConfirmation = DocumentMapper.mapDocumentField(dto.getEarnestMoneyDepositConfirmation());
    }

}