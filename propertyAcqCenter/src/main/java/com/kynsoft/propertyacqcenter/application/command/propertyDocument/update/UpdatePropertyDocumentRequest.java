package com.kynsoft.propertyacqcenter.application.command.propertyDocument.update;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePropertyDocumentRequest {

    private String fileName;
    private String property;
    private String filePath;

    private Boolean ownersContractRead;
    private Boolean assignmentOfContractRead;
    private LocalDate closingDate;
    private String platMapOrSurvey;
    private String earnestMoneyDeposit;
}
