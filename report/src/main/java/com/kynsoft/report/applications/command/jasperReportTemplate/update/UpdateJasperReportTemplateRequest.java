package com.kynsoft.report.applications.command.jasperReportTemplate.update;

import com.kynsoft.report.domain.dto.JasperReportTemplateType;
import com.kynsoft.report.domain.dto.status.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class    UpdateJasperReportTemplateRequest {
    private String code;
    private String name;
    private String description;
    private JasperReportTemplateType type;
    private String file;
    private String fileBase64; // Nuevo campo para el archivo en formato base64
    private String parameters;
    private UUID dbConection;
    private String query;
    private Status status;
}
