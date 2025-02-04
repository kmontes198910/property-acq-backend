package com.kynsoft.report.domain.dto;

import com.kynsoft.report.domain.dto.status.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JasperReportTemplateDto {
    private  UUID id;
    private  String templateCode;
    private  String templateName;
    private  String templateDescription;
    private  String templateContentUrl;
    private  JasperReportTemplateType type;
    private LocalDateTime createdAt;
    private DBConnectionDto dbConection;
    private Status status;

    public JasperReportTemplateDto(UUID id, String templateCode, String templateName, String templateDescription,
                                   String templateContentUrl, JasperReportTemplateType type) {
        this.id = id;
        this.templateCode = templateCode;
        this.templateName = templateName;
        this.templateDescription = templateDescription;
        this.templateContentUrl = templateContentUrl;
        this.type = type;
    }

    public JasperReportTemplateDto(UUID id, String templateCode, String templateName, String templateDescription,
                                   String templateContentUrl, JasperReportTemplateType type,
                                   DBConnectionDto dbConection, Status status) {
        this.id = id;
        this.templateCode = templateCode;
        this.templateName = templateName;
        this.templateDescription = templateDescription;
        this.templateContentUrl = templateContentUrl;
        this.type = type;
        this.dbConection = dbConection;

        this.status = status;
    }
}
