package com.kynsoft.report.applications.command.generateReport;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.report.domain.dto.DBConnectionDto;
import com.kynsoft.report.domain.dto.JasperReportTemplateDto;
import com.kynsoft.report.domain.services.IJasperReportTemplateService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GenerateReportCommandHandler implements ICommandHandler<GenerateReportCommand> {
    private static final Logger logger = LoggerFactory.getLogger(GenerateReportCommandHandler.class);
    private static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";
    
    @Value("${jasperreports.virtualizer.directory:/tmp/jasper-temp}")
    private String virtualizerDirectory;
    
    @Value("${jasperreports.virtualizer.max-size:2000}")
    private int virtualizerMaxSize;
    
    @Value("${jasperreports.jdbc-fetch-size:1000}")
    private int jdbcFetchSize;
    
    @Value("${report.max-file-size-mb:50}")
    private int maxFileSizeMb;
    
    // Caché para JasperReport compilados para evitar decodificar Base64 repetidamente
    private final ConcurrentHashMap<String, JasperReport> jasperReportCache = new ConcurrentHashMap<>();
    
    private final IJasperReportTemplateService jasperReportTemplateService;

    public GenerateReportCommandHandler(IJasperReportTemplateService jasperReportTemplateService) {
        this.jasperReportTemplateService = jasperReportTemplateService;
    }

    @Override
    public void handle(GenerateReportCommand command) {
        long startTime = System.currentTimeMillis();
        logger.debug("Iniciando generación de reporte: {}", command.getJasperReportCode());
        
        JasperReportTemplateDto reportTemplateDto = jasperReportTemplateService.findByTemplateCode(command.getJasperReportCode());
        try {
            validateReportTemplate(reportTemplateDto);
            
            byte[] response;
            if (Objects.equals(command.getReportFormatType(), "XLS")) {
                response = generateExcelReport(command.getParameters(), reportTemplateDto);
            } else {
                response = generatePdfReport(command.getParameters(), reportTemplateDto);
            }
            command.setResult(response);
            
            long endTime = System.currentTimeMillis();
            logger.debug("Reporte generado en {} ms. Tamaño: {} bytes", 
                    (endTime - startTime), response.length);
        } catch (Exception e) {
            logger.error("Error generating report: {}", e.getMessage(), e);
            throw new RuntimeException("Error generating report", e);
        }
    }

    private void validateReportTemplate(JasperReportTemplateDto reportTemplateDto) {
        if (reportTemplateDto == null || reportTemplateDto.getDbConection() == null) {
            throw new IllegalArgumentException("Database connection details are missing.");
        }
        
        // Verificar si existe el archivo en el sistema de archivos
        boolean fileExists = false;
        String templateContentUrl = reportTemplateDto.getTemplateContentUrl();
        if (templateContentUrl != null && !templateContentUrl.isEmpty()) {
            java.io.File jasperFile = new java.io.File(templateContentUrl);
            if (jasperFile.exists() && jasperFile.isFile() && jasperFile.canRead()) {
                fileExists = true;
                logger.debug("Archivo Jasper encontrado en el sistema de archivos: {}", templateContentUrl);
            } else {
                logger.debug("Archivo Jasper no encontrado en el sistema de archivos: {}", templateContentUrl);
            }
        }
        
        // Verificar si existe el contenido en Base64
        boolean base64Exists = reportTemplateDto.getJasperContentBase64() != null && 
                              !reportTemplateDto.getJasperContentBase64().isEmpty();
        
        // Lanzar error solo si no existe ni el archivo ni el contenido Base64
        if (!fileExists && !base64Exists) {
            throw new IllegalArgumentException("No se encontró el archivo Jasper en el sistema de archivos ni contenido Base64 en la base de datos.");
        }
    }

    public byte[] generatePdfReport(Map<String, Object> parameters, JasperReportTemplateDto reportTemplateDto) {
        // Crear un virtualizador de archivos para reportes grandes
        JRFileVirtualizer virtualizer = new JRFileVirtualizer(virtualizerMaxSize, virtualizerDirectory);
        parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
        
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             Connection connection = createConnection(reportTemplateDto)) {
            
            // Configura la conexión para mejorar el rendimiento
            configureConnectionForPerformance(connection);

            // Carga el informe compilado utilizando caché
            JasperReport jasperReport = getJasperReport(reportTemplateDto);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            // Verificar el tamaño del archivo generado
            validateFileSize(outputStream.size());
            
            return outputStream.toByteArray();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver not found. Ensure it is included in the classpath.", e);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database. Verify credentials and connection settings.", e);
        } catch (JRException | IOException e) {
            throw new RuntimeException("Error generating the JasperReport.", e);
        } finally {
            virtualizer.cleanup();
        }
    }

    private void configureConnectionForPerformance(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        
        // Configurar el tamaño del fetch size para optimizar consultas grandes
        if (connection.getMetaData().getDriverName().toLowerCase().contains("postgresql")) {
            // Para PostgreSQL
            connection.setReadOnly(true);
            try {
                // Usar reflection para manejar diferentes versiones del driver
                Class<?> pgConnectionClass = Class.forName("org.postgresql.PGConnection");
                Object pgConnection = connection.unwrap(pgConnectionClass);
                
                // Llamar a setPrepareThreshold mediante reflection
                Method setPrepareThresholdMethod = pgConnectionClass.getMethod("setPrepareThreshold", int.class);
                setPrepareThresholdMethod.invoke(pgConnection, 4);
                
                logger.debug("PGConnection configurado correctamente usando reflection");
            } catch (Exception e) {
                logger.warn("No se pudo configurar PGConnection: {}", e.getMessage());
                // Continuar sin esta optimización específica
            }
            
            // Establecer el fetch size
            connection.createStatement().setFetchSize(jdbcFetchSize);
        }
    }

    private JasperReport getJasperReport(JasperReportTemplateDto reportTemplateDto) throws JRException, IOException {
        String reportKey = reportTemplateDto.getId().toString();
        
        // Si hay actualizaciones recientes en la plantilla, no usar caché
        if (isRecentlyUpdated(reportTemplateDto)) {
            logger.debug("Plantilla de reporte actualizada recientemente, recargando");
            jasperReportCache.remove(reportKey);
        }
        
        // Intentar obtener de caché primero
        return jasperReportCache.computeIfAbsent(reportKey, key -> {
            try {
                // Primero, intentar cargar desde el sistema de archivos si existe la ruta del archivo
                String templateContentUrl = reportTemplateDto.getTemplateContentUrl();
                if (templateContentUrl != null && !templateContentUrl.isEmpty()) {
                    java.io.File jasperFile = new java.io.File(templateContentUrl);
                    if (jasperFile.exists() && jasperFile.isFile() && jasperFile.canRead()) {
                        logger.debug("Cargando archivo Jasper desde el sistema de archivos: {}", templateContentUrl);
                        try {
                            return (JasperReport) JRLoader.loadObject(jasperFile);
                        } catch (JRException e) {
                            logger.warn("Error al cargar archivo Jasper desde el sistema de archivos: {}. Intentando desde Base64", e.getMessage());
                            // Continuar y usar el contenido Base64 como respaldo
                        }
                    } else {
                        logger.debug("Archivo Jasper no encontrado en el sistema de archivos: {}. Usando Base64", templateContentUrl);
                    }
                }
                
                // Si no se pudo cargar desde el sistema de archivos, usar el contenido Base64
                logger.debug("Cargando archivo Jasper desde el contenido Base64 almacenado en la base de datos");
                return loadCompiledJasperFromBase64(reportTemplateDto.getJasperContentBase64());
            } catch (Exception e) {
                logger.error("Error loading jasper report: {}", e.getMessage(), e);
                throw new RuntimeException("Error loading jasper report", e);
            }
        });
    }
    
    // Verifica si la plantilla ha sido actualizada recientemente
    private boolean isRecentlyUpdated(JasperReportTemplateDto reportTemplateDto) {
        // Si no hay fecha de creación, considerar como nuevo
        if (reportTemplateDto.getCreatedAt() == null) {
            return true;
        }
        
        // Comparar con un umbral de tiempo, por ejemplo 5 minutos
        long thresholdMinutes = 5;
        return reportTemplateDto.getCreatedAt()
                .isAfter(java.time.LocalDateTime.now().minusMinutes(thresholdMinutes));
    }

    private void validateFileSize(int size) {
        int maxFileSize = getMaxFileSize();
        if (size > maxFileSize) {
            throw new RuntimeException("The generated report is too large. Size: " + size + " bytes, Maximum allowed: " + maxFileSize + " bytes.");
        }
    }

    /**
     * Carga un JasperReport compilado desde una cadena base64
     */
    private JasperReport loadCompiledJasperFromBase64(String base64Content) {
        if (base64Content == null || base64Content.isEmpty()) {
            throw new IllegalArgumentException("El contenido base64 del reporte está vacío");
        }
        
        try {
            byte[] jasperBytes = java.util.Base64.getDecoder().decode(base64Content);
            try (InputStream inputStream = new ByteArrayInputStream(jasperBytes)) {
                return (JasperReport) JRLoader.loadObject(inputStream);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error cargando el archivo .jasper desde la base de datos", e);
        }
    }

    private int getMaxFileSize() {
        return maxFileSizeMb * 1024 * 1024;
    }

    public byte[] generateExcelReport(Map<String, Object> parameters, JasperReportTemplateDto reportTemplateDto) throws JRException, IOException {
        // Usar virtualizador para manejar reportes grandes
        JRFileVirtualizer virtualizer = new JRFileVirtualizer(virtualizerMaxSize, virtualizerDirectory);
        parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             Connection connection = createConnection(reportTemplateDto)) {

            // Configurar la conexión para mejorar rendimiento
            configureConnectionForPerformance(connection);
            
            // Usar caché para los JasperReports compilados
            JasperReport jasperReport = getJasperReport(reportTemplateDto);
            logger.debug("Generando reporte Excel con base de datos: {}", reportTemplateDto.getDbConection().getName());

            // Llenar el reporte directamente con la conexión optimizada
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            // Configuración optimizada para la exportación a Excel
            exportToExcel(jasperPrint, outputStream);
            validateFileSize(outputStream.size());
            
            return outputStream.toByteArray();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error durante la generación del reporte Excel", e);
        } finally {
            virtualizer.cleanup();
        }
    }

    private void exportToExcel(JasperPrint jasperPrint, ByteArrayOutputStream outputStream) throws JRException {
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setDetectCellType(true);
        configuration.setCollapseRowSpan(false);
        configuration.setIgnoreGraphics(true); // Mejorar rendimiento ignorando gráficos complejos
        configuration.setWhitePageBackground(false); // Mejorar rendimiento
        configuration.setIgnoreCellBackground(false);
        configuration.setFontSizeFixEnabled(false);
        exporter.setConfiguration(configuration);

        exporter.exportReport();
    }

    private Connection createConnection(JasperReportTemplateDto reportTemplateDto) throws SQLException, ClassNotFoundException {
        DBConnectionDto dbConnection = reportTemplateDto.getDbConection();
        
        // Normalizar la URL de conexión a minúsculas para evitar problemas con el driver JDBC
        if (dbConnection.getUrl() != null) {
            String normalizedUrl = dbConnection.getUrl().toLowerCase();
            dbConnection.setUrl(normalizedUrl);
            logger.debug("URL de conexión normalizada: {}", normalizedUrl);
        }
        
        Class.forName(POSTGRESQL_DRIVER);
        
        // Configurar propiedades de conexión para optimizar rendimiento
        java.util.Properties props = new java.util.Properties();
        props.setProperty("user", dbConnection.getUsername());
        props.setProperty("password", dbConnection.getPassword());
        props.setProperty("ApplicationName", "JasperReportGenerator");
        props.setProperty("defaultRowFetchSize", String.valueOf(jdbcFetchSize));
        props.setProperty("reWriteBatchedInserts", "true");
        
        return DriverManager.getConnection(dbConnection.getUrl(), props);
    }
}