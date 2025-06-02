package com.kynsoft.propertyacqcenter.application.command.propertyDocument.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdatePropertyDocumentCommand implements ICommand {

    private UUID id;
    private String fileName;
    private String property;
    private String filePath;

    private Boolean ownersContractRead;
    private Boolean assignmentOfContractRead;
    private LocalDate closingDate;
    private String platMapOrSurvey;
    private String earnestMoneyDeposit;

    public UpdatePropertyDocumentCommand(UUID id, String fileName, String property, String filePath, 
                                         Boolean ownersContractRead, Boolean assignmentOfContractRead, 
                                         LocalDate closingDate, String platMapOrSurvey, String earnestMoneyDeposit) {
        this.id = id;
        this.fileName = fileName;
        this.property = property;
        this.filePath = filePath;
        this.ownersContractRead = ownersContractRead;
        this.assignmentOfContractRead = assignmentOfContractRead;
        this.closingDate = closingDate;
        this.platMapOrSurvey = platMapOrSurvey;
        this.earnestMoneyDeposit = earnestMoneyDeposit;
    }

    public static UpdatePropertyDocumentCommand fromRequest(UpdatePropertyDocumentRequest request, UUID id) {
        return new UpdatePropertyDocumentCommand(
                id,
                request.getFileName(),
                request.getProperty(),
                request.getFilePath(),
                request.getOwnersContractRead(),
                request.getAssignmentOfContractRead(),
                request.getClosingDate(),
                request.getPlatMapOrSurvey(),
                request.getEarnestMoneyDeposit()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdatePropertyDocumentMessage(id);
    }
}
