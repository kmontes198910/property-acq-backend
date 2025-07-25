package com.kynsoft.propertyacqcenter.domain.dto;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdquisitionDocumentDto implements IResponse {
    private String fileName;
    private String filePath;
}
