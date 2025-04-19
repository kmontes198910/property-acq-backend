package com.kynsoft.report.applications.command.generateReport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenerateReportRequest {
        private String jasperReportCode;
        private String reportFormatType;
        private Map<String, Object> parameters;
        private int CantCopy;
        private boolean enableCache = true; // Habilitado por defecto
        private String cacheKey; // Clave de caché personalizada (opcional)
        private boolean forceRefresh = false; // Forzar actualización ignorando caché
}
