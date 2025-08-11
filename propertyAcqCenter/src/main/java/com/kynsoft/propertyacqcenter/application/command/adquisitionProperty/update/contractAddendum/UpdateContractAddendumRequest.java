package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.contractAddendum;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateContractAddendumRequest {

    private List<UpdateDocumentContractAddendumRequest> contractAddendum;
    private LocalDate date;
}
