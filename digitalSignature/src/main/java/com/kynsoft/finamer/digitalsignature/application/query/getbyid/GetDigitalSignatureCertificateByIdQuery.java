package com.kynsoft.finamer.digitalsignature.application.query.getbyid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDigitalSignatureCertificateByIdQuery implements IQuery {
    private UUID id;
}