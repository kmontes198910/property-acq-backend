package com.kynsoft.invoiceservice.application.command.invoice.generate;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.invoiceservice.application.command.invoice.generate.request.CampoAdicionalRequest;
import com.kynsoft.invoiceservice.application.command.invoice.generate.request.DetalleFacturaRequest;
import com.kynsoft.invoiceservice.application.command.invoice.generate.request.PagoRequest;
import com.kynsoft.invoiceservice.application.query.customer.get.CustomerDto;
import com.kynsoft.invoiceservice.application.services.InvoiceService;
import com.kynsoft.invoiceservice.domain.dto.InvoiceIssuerDto;
import com.kynsoft.invoiceservice.domain.dto.InvoiceIssuingSequenceDto;
import com.kynsoft.invoiceservice.domain.exception.BusinessInvoiceException;
import com.kynsoft.invoiceservice.domain.exception.DomainErrorInvoiceMessage;
import com.kynsoft.invoiceservice.domain.service.ICustomerService;
import com.kynsoft.invoiceservice.domain.service.IInvoiceIssuerService;
import com.kynsoft.invoiceservice.infrastructure.entities.Customer;

import ec.e.facturacion.sri.constante.Ambiente;
import ec.e.facturacion.sri.constante.Estados;
import ec.e.facturacion.sri.modelo.ComprobanteBase;
import ec.e.facturacion.sri.modelo.Factura;
import ec.e.facturacion.sri.pdf.generador.FacturaPDFGenerador;
import ec.e.facturacion.sri.util.*;

import ec.e.facturacion.sri.ws.autorizacion.prueba.RespuestaComprobante;
import ec.e.facturacion.sri.ws.recepcion.prueba.RespuestaSolicitud;
import ec.e.facturacion.sri.ws.soap.servicio.SRIAutorizacionServicio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ec.e.facturacion.sri.constante.Regimen;
import ec.e.facturacion.sri.modelo.ComprobanteBase;
import ec.e.facturacion.sri.modelo.Factura;
import ec.e.facturacion.sri.ws.soap.servicio.SRIRecepcionServicio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenerateInvoiceCommandHandler implements ICommandHandler<GenerateInvoiceCommand> {

    private final InvoiceService invoiceService;
    private final ICustomerService customerService;
    private final IInvoiceIssuerService invoiceIssuerService;

    @Override
    @Transactional
    public void handle(GenerateInvoiceCommand command) {
        log.info("Generando nueva factura para el emisor ID: {}", command.getIssuerId());

        try {
            // 1. Obtener el emisor por su ID (o el emisor activo si no se proporcionó un ID)
            InvoiceIssuerDto issuerDto = invoiceIssuerService.getById(command.getIssuerId());

            // 2. Obtener y actualizar el secuencial para facturas del emisor
            String sequential = getNextSequentialFromIssuer(issuerDto, "01"); // 01 es el tipo de documento para facturas

            // 3. Buscar o crear el cliente (Customer) utilizando el servicio
            Customer customer = findOrUpdateCustomer(command);

            Factura factura = createfactura(issuerDto, sequential, command.getDetalles(), customer, command.getPropina(),
                    command.getPagos(), command.getInfoAdicional());

            if (validateInvoice(factura)) {
                log.error("Factura no válida, abortando generación.");
                throw new BusinessInvoiceException(DomainErrorInvoiceMessage.INVOICE_NOT_VALID,
                        "Factura no válida, verifique los datos.");
            }
            ;


            ByteArrayOutputStream xmlFactura = generateXmlAndInvoiceFile(factura);

            sendInvoiceSRI(xmlFactura, factura);


            command.setId(UUID.randomUUID()); // Asegurarse de que el comando tenga un ID
            command.setAccessKey(factura.getClaveAcceso());
            log.info("Factura generada con clave de acceso: {}", factura.getClaveAcceso());
        } catch (BusinessInvoiceException e) {
            log.error("Error de negocio al generar factura: {}", e.getMessage());
            throw e;
        } catch (IOException e) {
            log.error("Error de E/S al generar factura: {}", e.getMessage(), e);
            throw new BusinessInvoiceException(DomainErrorInvoiceMessage.GENERAL_ERROR,
                    "Error al generar archivos XML/PDF de factura: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error al generar factura: {}", e.getMessage(), e);
            throw new BusinessInvoiceException(DomainErrorInvoiceMessage.GENERAL_ERROR,
                    "Error al generar factura: " + e.getMessage());
        }
    }

    private static void sendInvoiceSRI(ByteArrayOutputStream xmlFactura, Factura factura) {
        try {
            // Crear instancia del servicio (true para modo prueba)
            SRIRecepcionServicio sriRecepcion = new SRIRecepcionServicio();

            // Enviar el comprobante al SRI para recepcionar
            RespuestaSolicitud respuestaRecepcion = sriRecepcion.enviarComprobante(xmlFactura.toByteArray(),
                    Ambiente.PRUEBA);

            // Imprimir la respuesta
            SRIImprimirRecepcionUtil.imprimirRespuestaRecepcion(respuestaRecepcion);

            if (respuestaRecepcion.getEstado().equals(Estados.RECIBIDA))
                try {
                    // Crear instancia del servicio (true para modo prueba)
                    SRIAutorizacionServicio sriAutorizacion = new SRIAutorizacionServicio();

                    // Enviar el comprobante al SRI para autorizar
                    RespuestaComprobante respuestaAutorizacion = sriAutorizacion
                            .autorizarComprobante(factura.getClaveAcceso(), Ambiente.PRUEBA);

                    SRIImprimirAutorizacionUtil.imprimirRespuestaAutorizacion(respuestaAutorizacion);

                } catch (Exception e) {
                    e.printStackTrace();
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ByteArrayOutputStream generateXmlAndInvoiceFile(Factura factura) throws IOException {
        // Generar el XML
        byte[] p12Bytes = FileConverterUtil.p12FileToByteArray("/Users/keimermontes/Development/medinec/scheduled/invoice-service/libs/0961881992.p12");

        // Construir el p12 a partir del arreglo de bytes
        InputStream p12Stream = FileConverterUtil.byteArrayToInputStream(p12Bytes);
        ByteArrayOutputStream xmlFactura = factura.generarXml(p12Stream, "Gloria2014");

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
//        // Generar el XML
//        byte[] p12Bytes = FileConverterUtil.p12FileToByteArray("/Users/keimermontes/Development/medinec/scheduled/invoice-service/libs/0961881992.p12");
//
//        // Construir el p12 a partir del arreglo de bytes
//        InputStream p12Stream = FileConverterUtil.byteArrayToInputStream(p12Bytes);
//        ByteArrayOutputStream xmlFactura = factura.generarXml(p12Stream, "Gloria2014");
//
//        System.out.println("Estado de la factura:\n" + factura.getEstado());
//        System.out.println("XML generado:\n" + xmlFactura);
//
//        // Guardar el XML en un archivo
//        String nombreArchivo = "factura_" + factura.getEstab() + "-" + factura.getPtoEmi() + "-"
//                               + factura.getSecuencial() + ".xml";
//
//        Files.write(Paths.get(nombreArchivo), xmlFactura.toByteArray());
//        System.out.println("\nFactura guardada en: " + nombreArchivo);

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


    private static boolean validateInvoice(Factura factura) {
        List<MensajeSRI> mensajes = factura.validar();
        if (!mensajes.isEmpty()) {
            SRIImprimirValidacionUtil.imprimirResultadoValidacion(mensajes);
            return true;
        }
        return false;
    }

    private String getNextSequentialFromIssuer(InvoiceIssuerDto issuer, String documentType) {
        // Buscar la secuencia para el tipo de documento específico
        Optional<InvoiceIssuingSequenceDto> sequenceOpt = issuer.getSequences().stream()
                .filter(seq -> documentType.equals(seq.getDocumentType()) && Boolean.TRUE.equals(seq.getIsActive()))
                .findFirst();

        InvoiceIssuingSequenceDto sequenceDto;

        if (sequenceOpt.isPresent()) {
            sequenceDto = sequenceOpt.get();
        } else {
            // Si no existe una secuencia para este tipo de documento, lanzar una excepción
            throw new BusinessInvoiceException(
                    DomainErrorInvoiceMessage.SEQUENCE_NOT_FOUND,
                    "No se encontró una secuencia activa para el tipo de documento: " + documentType);
        }

        // Incrementar el secuencial
        Long nextSequential = sequenceDto.getCurrentSequential() + 1;
        sequenceDto.setCurrentSequential(nextSequential);
        sequenceDto.setLastUsedDate(LocalDateTime.now());

        // Formatear a 9 dígitos
        return String.format("%09d", nextSequential);
    }

    private Factura createfactura(InvoiceIssuerDto issuer, String sequential, List<DetalleFacturaRequest> detallesDTO,
                                  Customer customer, BigDecimal propina, List<PagoRequest> pagos, List<CampoAdicionalRequest> infoAdicional) {
        // Información básica de la factura
        String ruc = issuer.getRuc();
        String razonSocial = issuer.getBusinessName();
        String estab = issuer.getEstablishment();
        String ptoEmi = issuer.getEmissionPoint();
        String dirMatriz = issuer.getAddress();
        String correo = issuer.getEmail();
        String telefono = issuer.getPhone();
        String fechaEmision = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Convertir DetalleFacturaDTO a Factura.DetalleFactura
        List<Factura.DetalleFactura> detalles = detallesDTO.stream()
                .map(detalleDTO -> {
                    // Crear el impuesto IVA básico necesario
                    Factura.Impuesto impuestoIva = Factura.Impuesto.IVA(
                            detalleDTO.getTipoImpuesto(),
                            detalleDTO.getPorcentajeImpuesto());

                    // Crear el builder con los campos requeridos
                    Factura.DetalleFactura.Builder builder = new Factura.DetalleFactura.Builder(
                            detalleDTO.getCodigoPrincipal(),
                            detalleDTO.getDescripcion(),
                            detalleDTO.getCantidad(),
                            detalleDTO.getPrecioUnitario(),
                            impuestoIva);

                    // Añadir campos opcionales si existen
                    if (detalleDTO.getUnidadMedida() != null) {
                        builder.withUnidadMedida(detalleDTO.getUnidadMedida());
                    }

                    if (detalleDTO.getDescuento() != null) {
                        builder.withDescuento(detalleDTO.getDescuento());
                    }

                    // Añadir impuesto ICE si existe
                    if (detalleDTO.getCodigoImpuestoICE() != null && detalleDTO.getPorcentajeImpuestoICE() != null) {
                        builder.withImpuestoICE(Factura.Impuesto.ICE(
                                detalleDTO.getCodigoImpuestoICE(),
                                detalleDTO.getPorcentajeImpuestoICE()));
                    }

                    return builder.build();
                })
                .collect(Collectors.toList());

        // Información adicional
        // En el caso que dese mandar informacion adicional aparte de el correo y telefono del comprador
        // lo puede especificar de esta forma
        List<ComprobanteBase.CampoAdicional> infoAdicionalList = infoAdicional.stream()
                .map(campoAdicionalDTO -> new ComprobanteBase.CampoAdicional(
                        campoAdicionalDTO.getNombre(),
                        campoAdicionalDTO.getValor()))
                .toList();


        List<Factura.Pago> payments = pagos.stream()
                .map(pagoDTO -> new Factura.Pago(
                        pagoDTO.getFormaPago(),
                        pagoDTO.getDescripcion(),
                        pagoDTO.getPlazo(),
                        pagoDTO.getUnidadTiempo()))
                .collect(Collectors.toList());
        String obligadoContabilidad = issuer.getPointOfSale() ? "SI" : "NO";
        String customerIdentificationType = customer.getIdType().getCode(); // Obtener el código ("04", "05", etc) en lugar del nombre del enumerado
        // Construir la factura
        Factura factura = new Factura.Builder(ruc, razonSocial, dirMatriz, correo, telefono, estab, ptoEmi, sequential, fechaEmision, detalles)
                .withNombreComercial(razonSocial)
                .withObligadoContabilidad(obligadoContabilidad)
                .withAgenteRetencion(issuer.getRetentionAgent())//?
                .withContribuyenteEspecial(issuer.getSpecialTaxpayer())//?
                .withContribuyenteRimpe(issuer.getRimpeRegime())//?
                .withTipoIdentificacionComprador(customerIdentificationType)
                .withRazonSocialComprador(customer.getBusinessName())
                .withIdentificacionComprador(customer.getIdNumber())
                .withDireccionComprador(customer.getAddress())
                .withCorreoComprador(customer.getAddress())
                .withTelefonoComprador(customer.getPhone())
                .withPropina(propina)
                .withPagos(payments)
                .withInfoAdicional(infoAdicionalList)
                .build();

        if (issuer.getRetentionAgent() != null) {
            factura.setAgenteRetencion(issuer.getRetentionAgent());
        }
        if (issuer.getSpecialTaxpayer() != null) {
            factura.setContribuyenteEspecial(issuer.getSpecialTaxpayer());
        }

        return factura;
    }

    private Customer findOrUpdateCustomer(GenerateInvoiceCommand request) {
        CustomerDto customerDto = null;

        try {
            // Intentar buscar el cliente por su número de identificación
            customerDto = customerService.findByIdentificationNumber(request.getCustomer().getIdentificationNumber());

            // Cliente encontrado, verificar si hay que actualizarlo
            if (!customerDto.getBusinessName().equals(request.getCustomer().getBusinessName()) ||
                (customerDto.getAddress() != null && !customerDto.getAddress().equals(request.getCustomer().getAddress())) ||
                (customerDto.getEmail() != null && !customerDto.getEmail().equals(request.getCustomer().getEmail())) ||
                (customerDto.getPhoneNumber() != null && !customerDto.getPhoneNumber().equals(request.getCustomer().getPhoneNumber()))) {

                // Actualizar los campos del cliente
                customerDto.setBusinessName(request.getCustomer().getBusinessName());
                customerDto.setAddress(request.getCustomer().getAddress());
                customerDto.setEmail(request.getCustomer().getEmail());
                customerDto.setPhoneNumber(request.getCustomer().getPhoneNumber());

                // Actualizar el cliente a través del servicio
                customerService.update(customerDto);
            }
        } catch (Exception e) {
            // Cliente no encontrado, crearlo
            customerDto = CustomerDto.builder()
                    .id(UUID.randomUUID())
                    .identificationType(request.getCustomer().getIdentificationType())
                    .identificationNumber(request.getCustomer().getIdentificationNumber())
                    .businessName(request.getCustomer().getBusinessName())
                    .address(request.getCustomer().getAddress())
                    .email(request.getCustomer().getEmail())
                    .phoneNumber(request.getCustomer().getPhoneNumber())
                    .isActive(true)
                    .createdBy(request.getCreatedBy())
                    .updatedBy(request.getCreatedBy())
                    .build();

            // Crear el cliente a través del servicio
            UUID customerId = customerService.create(customerDto);
            customerDto.setId(customerId);
        }

        // Convertir el DTO a la entidad Customer para mantener la compatibilidad con el resto del método
        return Customer.builder()
                .id(customerDto.getId())
                .idType(customerDto.getIdentificationType())
                .idNumber(customerDto.getIdentificationNumber())
                .businessName(customerDto.getBusinessName())
                .address(customerDto.getAddress())
                .email(customerDto.getEmail())
                .phone(customerDto.getPhoneNumber())
                .isActive(customerDto.getIsActive())
                .build();
    }

}