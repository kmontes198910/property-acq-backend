package com.kynsoft.report.infrastructure.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsoft.report.domain.dto.JasperReportTemplateDto;
import com.kynsoft.report.domain.services.IJasperReportTemplateService;
import com.kynsoft.report.domain.services.IReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService implements IReportService {
    private final RestTemplate restTemplate;
    private final ResourceLoader resourceLoader;
    private final IJasperReportTemplateService jasperReportTemplateService;
    
    @Value("${jasper.reports.output.path:/app/jasper-output}")
    private String jasperOutputPath;

    public ReportService(RestTemplate restTemplate, ResourceLoader resourceLoader, 
                         IJasperReportTemplateService jasperReportTemplateService) {
        this.restTemplate = restTemplate;
        this.resourceLoader = resourceLoader;
        this.jasperReportTemplateService = jasperReportTemplateService;
    }

    @Override
    public byte[] generatePdfReport(Map<String, Object> parameters, String jrxmlUrl, JREmptyDataSource jrEmptyDataSource) {
        JasperReport jasperReport = null;
        
        try {
            // Validar que la URL no sea nula
            if (jrxmlUrl == null) {
                throw new IllegalArgumentException("La URL del archivo JRXML no puede ser nula");
            }
            
            // Intentar determinar si hay un archivo Jasper local disponible
            // El URL puede ser una ruta de archivo local o una URL remota
            if (jrxmlUrl.contains("templateCode=")) {
                // Si el URL contiene templateCode, intentamos recuperar el archivo Jasper de la base de datos
                String templateCode = jrxmlUrl.substring(jrxmlUrl.indexOf("templateCode=") + "templateCode=".length());
                if (templateCode.contains("&")) {
                    templateCode = templateCode.substring(0, templateCode.indexOf("&"));
                }
                
                try {
                    JasperReportTemplateDto template = jasperReportTemplateService.findByTemplateCode(templateCode);
                    if (template.getTemplateContentUrl() != null && !template.getTemplateContentUrl().isEmpty()) {
                        // Verificar si el archivo existe en la ruta guardada
                        File jasperFile = new File(template.getTemplateContentUrl());
                        if (jasperFile.exists() && jasperFile.isFile()) {
                            System.out.println("Cargando archivo Jasper desde la ruta local: " + jasperFile.getAbsolutePath());
                            // Cargar directamente el archivo Jasper
                            jasperReport = (JasperReport) JRLoader.loadObject(jasperFile);
                        } else {
                            // Intentar usar el directorio configurado
                            String fileName = templateCode + ".jasper";
                            Path jasperPath = Paths.get(jasperOutputPath, fileName);
                            if (Files.exists(jasperPath)) {
                                System.out.println("Cargando archivo Jasper desde directorio configurado: " + jasperPath);
                                jasperReport = (JasperReport) JRLoader.loadObject(jasperPath.toFile());
                            }
                        }
                    }
                    
                    // Si no se pudo cargar desde archivo, usar el contenido Base64
                    if (jasperReport == null && template.getJasperContentBase64() != null && !template.getJasperContentBase64().isEmpty()) {
                        byte[] jasperBytes = Base64.getDecoder().decode(template.getJasperContentBase64());
                        InputStream jasperStream = new ByteArrayInputStream(jasperBytes);
                        jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
                        System.out.println("Cargando archivo Jasper desde contenido Base64 de la base de datos");
                    }
                } catch (Exception e) {
                    System.err.println("Error al cargar plantilla por código, continuando con método estándar: " + e.getMessage());
                }
            }
            
            // Si no se pudo cargar el informe Jasper de la base de datos o el sistema de archivos,
            // caemos de nuevo al método original - compilar desde JRXML
            if (jasperReport == null) {
                System.out.println("Compilando archivo JRXML desde URL: " + jrxmlUrl);
                byte[] jrxmlData = restTemplate.getForObject(jrxmlUrl, byte[].class);
                if (jrxmlData != null) {
                    InputStream inputStream = new ByteArrayInputStream(jrxmlData);
                    jasperReport = JasperCompileManager.compileReport(inputStream);
                } else {
                    throw new RuntimeException("No se pudo descargar el archivo JRXML desde: " + jrxmlUrl);
                }
            }
            
            // Imprimir parámetros para debugging
            if (parameters != null) {
                parameters.forEach((key, value) -> System.out.println(key + ": " + value));
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrEmptyDataSource);
            JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
            
            return byteArrayOutputStream.toByteArray();
        } catch (JRException e) {
            throw new RuntimeException("Error al generar el reporte PDF: " + e.getMessage(), e);
        }
    }

    @Override
    public String getReportParameters(String jrxmlUrl) {
        if (jrxmlUrl == null) {
            throw new IllegalArgumentException("La URL del archivo JRXML no puede ser nula");
        }
        
        // Obtener el archivo JRXML como un arreglo de bytes desde la URL
        byte[] data = restTemplate.getForObject(jrxmlUrl, byte[].class);
        if (data == null) {
            throw new RuntimeException("No se pudo descargar el archivo JRXML desde: " + jrxmlUrl);
        }
        
        InputStream inputStream = new ByteArrayInputStream(data);
        JasperReport jasperReport = null;
        try {
            jasperReport = JasperCompileManager.compileReport(inputStream);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }

        // Preparar el mapa para almacenar los detalles de los parámetros
        Map<String, Map<String, String>> paramMap = new HashMap<>();
        for (JRParameter param : jasperReport.getParameters()) {
            if (!param.isSystemDefined() && param.isForPrompting()) { // Solo parámetros definidos por el usuario y que son promptables
                Map<String, String> details = new HashMap<>();
                details.put("description", param.getDescription() != null ? param.getDescription() : "No description");
                details.put("type", param.getValueClassName());  // Añadir tipo de dato
                paramMap.put(param.getName(), details);
            }
        }

        // Convertir el mapa a una cadena JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(paramMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
