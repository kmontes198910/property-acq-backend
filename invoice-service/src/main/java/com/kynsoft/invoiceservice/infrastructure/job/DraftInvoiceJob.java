package com.kynsoft.invoiceservice.infrastructure.job;

import com.kynsoft.invoiceservice.domain.exception.BusinessInvoiceException;
import com.kynsoft.invoiceservice.domain.exception.DomainErrorInvoiceMessage;
import com.kynsoft.invoiceservice.domain.service.IInvoiceIssuerService;
import com.kynsoft.invoiceservice.domain.service.impl.InvoiceService;
import com.kynsoft.invoiceservice.infrastructure.entities.Invoice;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceStatus;
import com.kynsoft.invoiceservice.infrastructure.repository.query.InvoiceRepository;
import ec.e.facturacion.sri.constante.Ambiente;
import ec.e.facturacion.sri.constante.Estados;
import ec.e.facturacion.sri.modelo.Factura;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.kynsoft.invoiceservice.infrastructure.mapper.MapperInvoice;
import com.kynsoft.invoiceservice.infrastructure.util.CredentialUtil;


@Slf4j
@Component
public class DraftInvoiceJob {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceService invoiceService;
    private final MapperInvoice mapperInvoice;
    @Value("${sri.ambiente}")
    private String sriAmbiente;

    public DraftInvoiceJob(
            InvoiceRepository invoiceRepository,
            InvoiceService invoiceService,
            IInvoiceIssuerService invoiceIssuerService,
            CredentialUtil credentialUtil,
            MapperInvoice mapperInvoice) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceService = invoiceService;
        this.mapperInvoice = mapperInvoice;
    }

    // Ejecuta cada día a la medianoche
    @Scheduled(cron = "0 * 4 * * *")
    @Transactional
    public void buscarFacturasEnBorrador() {
        // Primero obtenemos los IDs de facturas en estado DRAFT
        List<Invoice> facturasBorrador = invoiceRepository.findByStatus(InvoiceStatus.DRAFT);
        if (facturasBorrador.isEmpty()) {
            System.out.println("No se encontraron facturas en estado DRAFT.");
            return;
        }

        // Convertir cada Invoice a Factura utilizando el mapper
        List<ProcessInvoice> facturas = facturasBorrador.stream()
                .map(mapperInvoice::convertToFactura)
                .toList();

        System.out.println("Facturas en estado DRAFT encontradas: " + facturas.size());

        for (ProcessInvoice prepResult : facturas) {
            generateDocumentsAsync(prepResult.getFactura(), prepResult.getInvoiceId(),
                    UUID.randomUUID(), prepResult.getP12Bytes(), prepResult.getP12Password(), prepResult.getInvoiceLogo());
        }

        // Aquí puedes agregar la lógica que necesites con las facturas tipo Factura
    }
    public void generateDocumentsAsync(Factura factura, UUID invoice, UUID userId,  InputStream p12Bytes, String password, String invoiceLogo) {
        try {
            log.info("Iniciando generación asíncrona de documentos para factura: {}", invoice);
            ByteArrayOutputStream xmlFactura = generateXmlAndInvoiceFile(factura, p12Bytes, password);
             ByteArrayOutputStream pdfInvoice = generatePDFInvoice(factura, invoiceLogo);
             sendInvoiceSRI(xmlFactura, factura, invoice, userId);
            log.info("Documentos generados correctamente para factura: {}", invoice);
        } catch (Exception e) {
            invoiceService.changeStatus(invoice, InvoiceStatus.ERROR, userId);
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


    private static ByteArrayOutputStream generatePDFInvoice(Factura factura, String logoBase64) throws IOException {
        try {
            // Generar el PDF
          //  String logoBase64 = FileConverterUtil.imageToBase64("/Users/keimermontes/Development/medinec/scheduled/invoice-service/libs/logo.jpg");
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
