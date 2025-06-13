package com.kynsoft.invoiceservice.infrastructure.job;

import com.kynsoft.invoiceservice.domain.dto.InvoiceIssuerDto;
import com.kynsoft.invoiceservice.domain.exception.BusinessInvoiceException;
import com.kynsoft.invoiceservice.domain.exception.DomainErrorInvoiceMessage;
import com.kynsoft.invoiceservice.domain.service.IInvoiceIssuerService;
import com.kynsoft.invoiceservice.domain.service.impl.InvoiceService;
import com.kynsoft.invoiceservice.infrastructure.entities.Invoice;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceStatus;
import com.kynsoft.invoiceservice.infrastructure.repository.query.InvoiceDetailRepository;
import com.kynsoft.invoiceservice.infrastructure.repository.query.InvoiceRepository;
import com.kynsoft.invoiceservice.infrastructure.service.InvoiceLoaderService;
import ec.e.facturacion.sri.constante.Ambiente;
import ec.e.facturacion.sri.constante.Estados;
import ec.e.facturacion.sri.constante.Regimen;
import ec.e.facturacion.sri.modelo.Factura;
import ec.e.facturacion.sri.modelo.ComprobanteBase;
import ec.e.facturacion.sri.pdf.generador.FacturaPDFGenerador;
import ec.e.facturacion.sri.util.FileConverterUtil;
import ec.e.facturacion.sri.util.SRIImprimirAutorizacionUtil;
import ec.e.facturacion.sri.util.SRIImprimirRecepcionUtil;
import ec.e.facturacion.sri.ws.autorizacion.prueba.RespuestaComprobante;
import ec.e.facturacion.sri.ws.recepcion.prueba.RespuestaSolicitud;
import ec.e.facturacion.sri.ws.soap.servicio.SRIAutorizacionServicio;
import ec.e.facturacion.sri.ws.soap.servicio.SRIRecepcionServicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.kynsoft.invoiceservice.util.CredentialUtil;


@Slf4j
@Component
public class DraftInvoiceJob {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceService invoiceService;
    private final IInvoiceIssuerService invoiceIssuerService;
    private final CredentialUtil credentialUtil;
    @Value("${sri.ambiente}")
    private String sriAmbiente;

    public DraftInvoiceJob(
            InvoiceRepository invoiceRepository,
            InvoiceService invoiceService,
            IInvoiceIssuerService invoiceIssuerService,
            CredentialUtil credentialUtil) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceService = invoiceService;
        this.invoiceIssuerService = invoiceIssuerService;
        this.credentialUtil = credentialUtil;
    }

    // Ejecuta cada día a la medianoche
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void buscarFacturasEnBorrador() {
        // Primero obtenemos los IDs de facturas en estado DRAFT
        List<Invoice> facturasBorrador = invoiceRepository.findByStatus(InvoiceStatus.DRAFT);
        if (facturasBorrador.isEmpty()) {
            System.out.println("No se encontraron facturas en estado DRAFT.");
            return;
        }

        // Convertir cada Invoice a Factura
        List<ProcessInvoice> facturas = facturasBorrador.stream()
                .map(this::convertToFactura)
                .toList();

        System.out.println("Facturas en estado DRAFT encontradas: " + facturas.size());

        for (ProcessInvoice prepResult : facturas) {
            generateDocumentsAsync(prepResult.getFactura(), prepResult.getInvoiceId(),
                    UUID.randomUUID(), prepResult.getP12Bytes(), prepResult.getP12Password());
        }

        // Aquí puedes agregar la lógica que necesites con las facturas tipo Factura
    }

    /**
     * Convierte un objeto Invoice a un objeto Factura (ec.e.facturacion.sri.modelo.Factura)
     */
    private ProcessInvoice convertToFactura(Invoice invoice) {
        // Mapeo de campos básicos
        String ruc = invoice.getIssuer() != null ? invoice.getIssuer().getRuc() : "";
        String razonSocial = invoice.getIssuer() != null ? invoice.getIssuer().getBusinessName() : "";
        String nombreComercial = invoice.getIssuer() != null ? invoice.getIssuer().getCommercialName() : "";
        String estab = extraerEstab(invoice.getDocumentNumber());
        String ptoEmi = extraerPtoEmi(invoice.getDocumentNumber());
        String secuencial = invoice.getSequential();
        String dirMatriz = invoice.getIssuer() != null ? invoice.getIssuer().getAddress() : "";
        String correo = invoice.getCustomer() != null ? invoice.getCustomer().getEmail() : "";
        String telefono = invoice.getCustomer() != null ? invoice.getCustomer().getPhone() : "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String fechaEmision = invoice.getEmissionDate() != null
                ? invoice.getEmissionDate().toLocalDate().format(formatter)
                : "";

        // Detalles
        List<Factura.DetalleFactura> detalles = invoice.getDetails().stream().map(det -> {
            // Mapear todos los impuestos del detalle
            List<Factura.Impuesto> impuestos = new ArrayList<>(det.getTaxes().stream()
                    .map(t -> new Factura.Impuesto(t.getCode(), t.getPercentageCode(), t.getValue()))
                    .toList());

            // Buscar IVA, si no hay, usar el primero o null
            Factura.Impuesto principal = null;
            if (!impuestos.isEmpty()) {
                principal = impuestos.stream()
                        .filter(i -> i.getCodigo() != null && i.getCodigo().equals("2"))
                        .findFirst()
                        .orElse(impuestos.get(0));
            }


            return new Factura.DetalleFactura.Builder(
                    det.getMainCode(),
                    det.getDescription(),
                    det.getQuantity(),
                    det.getUnitPrice(),
                    principal
            )
                    .withUnidadMedida(det.getUnitOfMeasure())
                    .withDescuento(det.getDiscount() != null ? det.getDiscount() : java.math.BigDecimal.ZERO)
                    .build();
        }).toList();

        List<Factura.Pago> pagos = (invoice.getPayments().stream()
                .map(pagoDTO -> new Factura.Pago(
                        pagoDTO.getPaymentMethod(),
                        pagoDTO.getPaymentMethodName(),
                        pagoDTO.getAmount(),
                        pagoDTO.getTimeValue() != null ? pagoDTO.getTimeValue() : BigDecimal.ZERO,
                        pagoDTO.getTimeUnit()))
                .toList());

        // Información adicional
        List<ComprobanteBase.CampoAdicional> infoAdicional = new ArrayList<>(invoice.getAdditionalFields().stream().map(f ->
                new ComprobanteBase.CampoAdicional(f.getName(), f.getValue())
        ).toList());

        // Construir la factura
        Factura.Builder builder = new Factura.Builder(
                ruc, razonSocial, dirMatriz, correo, telefono, estab, ptoEmi, secuencial, fechaEmision, detalles
        );
        builder.withContribuyenteRimpe(Regimen.REGIMEN_RIMPE);

        builder.withTipoIdentificacionComprador(invoice.getCustomer() != null ? invoice.getCustomer().getIdType().getCode() : "05")
                .withRazonSocialComprador(invoice.getCustomer() != null ? invoice.getCustomer().getBusinessName() : "CONSUMIDOR FINAL")
                .withIdentificacionComprador(invoice.getCustomer() != null ? invoice.getCustomer().getIdNumber() : "9999999999999")
                .withDireccionComprador(invoice.getCustomer() != null ? invoice.getCustomer().getAddress() : "SIN DIRECCIÓN")
                .withCorreoComprador(invoice.getCustomer() != null ? invoice.getCustomer().getEmail() : "SIN CORREO")
                .withTelefonoComprador(invoice.getCustomer() != null ? invoice.getCustomer().getPhone() : "SIN TELÉFONO");

        builder.withNombreComercial(nombreComercial);

        if (!pagos.isEmpty()) {
            pagos.forEach(builder::withPago);
        }
        if (!infoAdicional.isEmpty()) {
            builder.withInfoAdicional(infoAdicional);
        }
        if (invoice.getTip() != null) {
            builder.withPropina(invoice.getTip());
        }
        // Puedes agregar más campos según lo requieras
        InvoiceIssuerDto invoiceIssuerDto = invoiceIssuerService.getById(invoice.getIssuer().getId());

        // Decodificar el certificado P12
        String base64 = invoiceIssuerDto.getDigitalCertP12().replaceAll("\\s+", "");
        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        InputStream p12Stream = new ByteArrayInputStream(decodedBytes);

        // Asegurar que la contraseña esté desencriptada usando el CredentialUtil
        // Esto garantiza que la contraseña sea usable incluso si por alguna razón
        // el AttributeEncryptor no la desencriptó automáticamente
        String rawPassword = invoiceIssuerDto.getDigitalCertPassword();
        String password = credentialUtil.ensureDecrypted(rawPassword);

        log.debug("Contraseña procesada para certificado digital del emisor: {}", invoice.getIssuer().getId());

        return new ProcessInvoice(invoice.getId(), builder.build(), p12Stream, password);
    }

    private String extraerEstab(String documentNumber) {
        if (documentNumber != null && documentNumber.contains("-")) {
            String[] parts = documentNumber.split("-");
            if (parts.length > 0) return parts[0];
        }
        return "001";
    }

    private String extraerPtoEmi(String documentNumber) {
        if (documentNumber != null && documentNumber.contains("-")) {
            String[] parts = documentNumber.split("-");
            if (parts.length > 1) return parts[1];
        }
        return "001";
    }

    public void generateDocumentsAsync(Factura factura, UUID invoice, UUID userId,  InputStream p12Bytes, String password) {
        try {
            log.info("Iniciando generación asíncrona de documentos para factura: {}", invoice);
            ByteArrayOutputStream xmlFactura = generateXmlAndInvoiceFile(factura, p12Bytes, password);
          //  ByteArrayOutputStream pdfInvoice = generatePDFInvoice(factura);
            sendInvoiceSRI(xmlFactura, factura, invoice, userId);
            log.info("Documentos generados correctamente para factura: {}", invoice);
        } catch (Exception e) {
            log.error("Error en generación asíncrona de documentos: {}", e.getMessage(), e);
        }
    }

    private static ByteArrayOutputStream generateXmlAndInvoiceFile(Factura factura, InputStream p12Stream, String password) throws IOException {
        // Generar el XML
     //   byte[] p12Bytes = FileConverterUtil.p12FileToByteArray("/Users/keimermontes/Development/medinec/scheduled/invoice-service/libs/0961881992.p12");

        // Construir el p12 a partir del arreglo de bytes
      //  InputStream p12Stream = FileConverterUtil.byteArrayToInputStream(p12Bytes);
        ByteArrayOutputStream xmlFactura = factura.generarXml(p12Stream, password);

        System.out.println("Estado de la factura:\n" + factura.getEstado());
        System.out.println("XML generado:\n" + xmlFactura);

        // Guardar el XML en un archivo
        String nombreArchivo = "factura_" + factura.getEstab() + "-" + factura.getPtoEmi() + "-"
                               + factura.getSecuencial() + ".xml";

        Files.write(Paths.get(nombreArchivo), xmlFactura.toByteArray());
        System.out.println("\nFactura guardada en: " + nombreArchivo);

        try {
            // Generar el PDF
            String logoBase64 = FileConverterUtil.imageToBase64("/Users/keimermontes/Development/medinec/scheduled/invoice-service/libs/logo.jpg");
            ByteArrayOutputStream pdfFactura = FacturaPDFGenerador.generarPDF(factura, logoBase64, "#2D4C80");

            // Guardar el PDF en un archivo
            String nombreArchivoPdf = "factura_" + factura.getEstab() + "-" + factura.getPtoEmi() + "-"
                                      + factura.getSecuencial() + ".pdf";

            Files.write(Paths.get(nombreArchivoPdf), pdfFactura.toByteArray());
            System.out.println("\nFactura PDF guardada en: " + nombreArchivoPdf);


        } catch (Exception e) {
            System.err.println("Error al generar o guardar el pdf de la factura : " + e.getMessage());
            e.printStackTrace();
        }


        return xmlFactura;
    }


    private static ByteArrayOutputStream generatePDFInvoice(Factura factura) throws IOException {
        try {
            // Generar el PDF
            String logoBase64 = FileConverterUtil.imageToBase64("/Users/keimermontes/Development/medinec/scheduled/invoice-service/libs/logo.jpg");
            ByteArrayOutputStream pdfFactura = FacturaPDFGenerador.generarPDF(factura, logoBase64, "#2D4C80");

            // Guardar el PDF en un archivo
            String nombreArchivoPdf = "factura_" + factura.getEstab() + "-" + factura.getPtoEmi() + "-"
                                      + factura.getSecuencial() + ".pdf";

            Files.write(Paths.get(nombreArchivoPdf), pdfFactura.toByteArray());
            System.out.println("\nFactura PDF guardada en: " + nombreArchivoPdf);

            return pdfFactura;
        } catch (Exception e) {
            throw new BusinessInvoiceException(DomainErrorInvoiceMessage.GENERAL_ERROR,
                    "Error al generar factura: " + e.getMessage());
        }

    }


    private void sendInvoiceSRI(ByteArrayOutputStream xmlFactura, Factura factura, UUID invoice, UUID userId) {

        // Crear instancia del servicio (true para modo prueba)
        SRIRecepcionServicio sriRecepcion = new SRIRecepcionServicio();

        // Enviar el comprobante al SRI para recepcionar
        Integer ambienteEnum = "PRODUCCION".equalsIgnoreCase(sriAmbiente) ? Ambiente.PRODUCCION : Ambiente.PRUEBA;

        RespuestaSolicitud respuestaRecepcion = sriRecepcion.enviarComprobante(xmlFactura.toByteArray(),
                ambienteEnum);

        // Obtener la clave de acceso de la factura
        //Cambiar el estado de la factura al estado que me responda
        //Mostrar el mensaje de error si no se recibe la respuesta guardar

        // Imprimir la respuesta
        SRIImprimirRecepcionUtil.imprimirRespuestaRecepcion(respuestaRecepcion);

        if (respuestaRecepcion.getEstado().equals(Estados.RECIBIDA)) {
            invoiceService.changeStatus(invoice, InvoiceStatus.RECEIVED, userId);
            // Crear instancia del servicio (true para modo prueba)
            SRIAutorizacionServicio sriAutorizacion = new SRIAutorizacionServicio();

            // Enviar el comprobante al SRI para autorizar
            RespuestaComprobante respuestaAutorizacion = sriAutorizacion
                    .autorizarComprobante(factura.getClaveAcceso(), Ambiente.PRUEBA);

            if (respuestaAutorizacion.getAutorizaciones().getAutorizacion().get(0).getEstado().equals(Estados.AUTORIZADO)) {
                invoiceService.changeStatus(invoice, InvoiceStatus.AUTHORIZED, userId);
            } else {

                invoiceService.changeStatus(invoice, InvoiceStatus.REJECTED, userId);
            }

            SRIImprimirAutorizacionUtil.imprimirRespuestaAutorizacion(respuestaAutorizacion);
        } else if (respuestaRecepcion.getEstado().equals(Estados.DEVUELTA)) {
            invoiceService.changeStatus(invoice, InvoiceStatus.REJECTED, userId);
            throw new BusinessInvoiceException(DomainErrorInvoiceMessage.GENERAL_ERROR,
                    "Factura rechazada por el SRI: " + respuestaRecepcion.getComprobantes().getComprobante().get(0).getMensajes().getMensaje().get(0).getMensaje());
        } else {
            invoiceService.changeStatus(invoice, InvoiceStatus.ERROR, userId);
            throw new BusinessInvoiceException(DomainErrorInvoiceMessage.GENERAL_ERROR,
                    "Estado de la factura no reconocido: " + respuestaRecepcion.getEstado());
        }

    }


}
