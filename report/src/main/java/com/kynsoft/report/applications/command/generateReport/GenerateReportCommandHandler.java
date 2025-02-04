package com.kynsoft.report.applications.command.generateReport;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.report.domain.dto.DBConnectionDto;
import com.kynsoft.report.domain.dto.JasperReportTemplateDto;
import com.kynsoft.report.domain.services.IJasperReportTemplateService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GenerateReportCommandHandler implements ICommandHandler<GenerateReportCommand> {
    private static final Logger logger = LoggerFactory.getLogger(GenerateReportCommandHandler.class);
    private final RestTemplate restTemplate;
    private final IJasperReportTemplateService jasperReportTemplateService;
    private final ResourceLoader resourceLoader;

    public GenerateReportCommandHandler(RestTemplate restTemplate,
                                        IJasperReportTemplateService jasperReportTemplateService,
                                        ResourceLoader resourceLoader) {
        this.restTemplate = restTemplate;
        this.jasperReportTemplateService = jasperReportTemplateService;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void handle(GenerateReportCommand command) {
        JasperReportTemplateDto reportTemplateDto = jasperReportTemplateService.findByTemplateCode(command.getJasperReportCode());
        try {
            byte[] response;
            if (Objects.equals(command.getReportFormatType(), "XLS")) {
                response = generateExcelReport(command.getParameters(), reportTemplateDto.getTemplateContentUrl(), reportTemplateDto);
            } else {
                response = generatePdfReport(command.getParameters(), reportTemplateDto.getTemplateContentUrl(), reportTemplateDto);
            }
            command.setResult(response);
        } catch (Exception e) {
            logger.error("Error generating report: ", e);
            throw new RuntimeException("Error generating report", e);
        }
    }

    public byte[] generatePdfReport(Map<String, Object> parameters, String reportPath, JasperReportTemplateDto reportTemplateDto) {
        Connection connection = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            // Verificar que el DTO contiene información válida
            if (reportTemplateDto == null || reportTemplateDto.getDbConection() == null) {
                throw new IllegalArgumentException("Database connection details are missing.");
            }

          JasperReport jasperReport = loadCompiledJasperFromUrl(reportTemplateDto.getTemplateContentUrl());
            if (jasperReport == null) {
                throw new IllegalArgumentException("Could not load JasperReport from provided URL.");
            }

            // Establecer la conexión con la base de datos
            DBConnectionDto dbConnection = reportTemplateDto.getDbConection();
            Class.forName("org.postgresql.Driver"); // Asegurar que el driver está cargado

            connection = DriverManager.getConnection(dbConnection.getUrl(),
                    dbConnection.getUsername(),
                    dbConnection.getPassword());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

//            // Verifica el tamaño del archivo generado
            if (outputStream.size() > getMaxFileSize()) {
                throw new RuntimeException("The generated PDF report is too large. Size: " + outputStream.size() + " bytes.");
            }
            return outputStream.toByteArray();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver not found. Ensure it is included in the classpath.", e);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database. Verify credentials and connection settings.", e);
        } catch (JRException e) {
            throw new RuntimeException("Error generating the JasperReport.", e);
        } finally {
            // Cerrar la conexión a la base de datos
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    System.err.println("Error closing database connection: " + ex.getMessage());
                }
            }
        }
    }

    public static JasperReport loadCompiledJasperFromUrl(String jasperUrl) {
        try (InputStream inputStream = new URL(jasperUrl).openStream()) {
            return (JasperReport) JRLoader.loadObject(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Error cargando el archivo .jasper desde la URL: " + jasperUrl, e);
        }
    }

//    private JasperReport loadJasperReportFromUrl(String templateUrl) {
//        try {
//
//            // Ruta del archivo jrxml
//            String jrxmlPath = "/Users/keimermontes/Downloads/receta_medica.jrxml";
//            // Ruta de salida para el .jasper compilado
//            String jasperPath = "/Users/keimermontes/Downloads/receta_medica.jasper";
//
//            // Compilar y guardar el .jasper en jasperPath
//            JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);
//            // Descargar contenido
//            byte[] bytes = new URL(templateUrl).openStream().readAllBytes();
//
//            // Compilar
//            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
//                return JasperCompileManager.compileReport(inputStream);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Error loading JRXML template from URL: " + templateUrl, e);
//        }
//    }

    private int getMaxFileSize() {
        // Define el límite en bytes (por ejemplo, 50 MB)
        return 50 * 1024 * 1024; // 50 MB
    }
//    public byte[] generatePdfReport1(Map<String, Object> parameters, String reportPath, JasperReportTemplateDto reportTemplateDto) throws JRException, IOException {
//        JRFileVirtualizer virtualized = new JRFileVirtualizer(2, "temp/");
//        parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualized);
//
//        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//             Connection connection = createConnection(reportTemplateDto)) {
//
//            JasperReport jasperReport = getJasperReport(reportPath);
//            logger.error("Generating PDF report with database: {}", reportTemplateDto.getDbConection().getName());
//
//            if (reportTemplateDto.getQuery() != null && !reportTemplateDto.getQuery().isEmpty()) {
//                JdbcTemplate jdbcTemplate = new JdbcTemplate(new SingleConnectionDataSource(connection, true));
//                String query = replaceQueryParameters(reportTemplateDto.getQuery(), parameters);
//                NamedParameterJdbcTemplate namedJdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
//                List<Map<String, Object>> rows = namedJdbc.queryForList(query, parameters);
//                JRDataSource jrDataSource = new JRBeanCollectionDataSource(rows);
//                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrDataSource);
//                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
//            } else {
//                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
//                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
//            }
//
//            return outputStream.toByteArray();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            virtualized.cleanup();
//        }
//    }

    public byte[] generateExcelReport(Map<String, Object> parameters, String reportPath, JasperReportTemplateDto reportTemplateDto) throws JRException, IOException {
        JRFileVirtualizer virtualizer = new JRFileVirtualizer(2, "temp/");
        parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             Connection connection = createConnection(reportTemplateDto)) {

            JasperReport jasperReport = loadCompiledJasperFromUrl(reportPath);
            logger.error("Generating Excel report with database: {}", reportTemplateDto.getDbConection().getName());

            JdbcTemplate jdbcTemplate = new JdbcTemplate(new SingleConnectionDataSource(connection, true));
            String query =  "";
            NamedParameterJdbcTemplate namedJdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
            query = replaceQueryParameters(query, parameters);
            List<Map<String, Object>> rows = namedJdbc.queryForList(query, parameters);

            JRDataSource jrDataSource = new JRBeanCollectionDataSource(rows);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrDataSource);

            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setDetectCellType(true);
            configuration.setCollapseRowSpan(false);
            exporter.setConfiguration(configuration);

            exporter.exportReport();
            return outputStream.toByteArray();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            virtualizer.cleanup();
        }
    }

    private Connection createConnection(JasperReportTemplateDto reportTemplateDto) throws SQLException {
        return DriverManager.getConnection(reportTemplateDto.getDbConection().getUrl(),
                reportTemplateDto.getDbConection().getUsername(),
                reportTemplateDto.getDbConection().getPassword());
    }

    private JasperReport getJasperReport(String reportPath) throws JRException, IOException {
        try (InputStream jrxmlInput = loadReportInputStream(reportPath)) {
            logger.error("JRXML content loaded successfully from: {}", reportPath);
            return JasperCompileManager.compileReport(jrxmlInput);
        }
    }

    private InputStream loadReportInputStream(String reportPath) throws IOException {
        logger.error("Buscar Archivo: templates/{}", reportPath);
        Resource localResource = resourceLoader.getResource("classpath:templates/" + reportPath);
        if (localResource.exists()) {
            logger.error("Loading JRXML template from local resources: templates/{}", reportPath);
            return localResource.getInputStream();
        } else {
            logger.warn("Local template not found, fetching JRXML template from URL: {}", reportPath);
            return new ByteArrayInputStream(Objects.requireNonNull(restTemplate.getForObject(reportPath, byte[].class)));
        }
    }

    private String replaceQueryParameters(String query, Map<String, Object> parameters) {
        Pattern pattern = Pattern.compile("::([a-zA-Z]\\w*)");
        Matcher matcher = pattern.matcher(query);
        StringBuffer resultQuery = new StringBuffer();

        while (matcher.find()) {
            String paramName = matcher.group(1);
            Object value = parameters.get(paramName);

            if (value == null) {
                throw new IllegalArgumentException("Parameter " + paramName + " not found in the parameters map.");
            }
            matcher.appendReplacement(resultQuery, value.toString());
        }
        matcher.appendTail(resultQuery);

        return resultQuery.toString();
    }
}