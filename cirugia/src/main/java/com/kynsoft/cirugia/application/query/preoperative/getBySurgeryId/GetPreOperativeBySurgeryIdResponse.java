package com.kynsoft.cirugia.application.query.preoperative.getBySurgeryId;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.PreOperative;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPreOperativeBySurgeryIdResponse implements IResponse {
    private PreOperative preOperative;
}