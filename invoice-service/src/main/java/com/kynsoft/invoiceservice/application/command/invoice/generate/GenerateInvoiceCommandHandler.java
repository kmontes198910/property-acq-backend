package com.kynsoft.invoiceservice.application.command.invoice.generate;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.invoiceservice.application.command.invoice.generate.request.CampoAdicionalRequest;
import com.kynsoft.invoiceservice.application.command.invoice.generate.request.DetalleFacturaRequest;
import com.kynsoft.invoiceservice.application.command.invoice.generate.request.PagoRequest;
import com.kynsoft.invoiceservice.application.query.customer.get.CustomerDto;
import com.kynsoft.invoiceservice.domain.dto.*;
import com.kynsoft.invoiceservice.domain.exception.BusinessInvoiceException;
import com.kynsoft.invoiceservice.domain.exception.DomainErrorInvoiceMessage;
import com.kynsoft.invoiceservice.domain.service.ICustomerService;
import com.kynsoft.invoiceservice.domain.service.IInvoiceIssuerService;
import com.kynsoft.invoiceservice.domain.service.impl.InvoiceService;
import com.kynsoft.invoiceservice.infrastructure.entities.Customer;

import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceStatus;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ec.e.facturacion.sri.ws.soap.servicio.SRIRecepcionServicio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    public String claveAcceso;
    @Value("${sri.ambiente}")
    private String sriAmbiente;

    @Override
    public void handle(GenerateInvoiceCommand command) {
        log.info("Generando nueva factura para el emisor ID: {}", command.getIssuerId());

        try {
            // Fase 1: Preparación de datos (no transaccional)
            // Esta fase puede tomar tiempo pero no requiere estar en una transacción
            log.info("Fase 1: Preparando datos para la factura");
            InvoicePreparationResult prepResult = prepareInvoiceData(command);

            // Fase 2: Persistencia de datos (transaccional)
            // Esta fase es rápida y solo interactúa con la base de datos
            log.info("Fase 2: Persistiendo la factura en base de datos");
            UUID invoiceId = invoiceService.create(prepResult.getInvoiceDto());

            // Actualización del comando con los resultados
            command.setId(invoiceId);
            command.setAccessKey(prepResult.getAccessKey());
            log.info("Factura generada con ID: {} y clave de acceso: {}", invoiceId, prepResult.getAccessKey());

            // Fase 3: Procesamiento posterior (no transaccional)
            // Esta fase puede ser ejecutada de forma asíncrona si es necesario
            // ya que la factura ya está guardada en la base de datos
        //    generateDocumentsAsync(prepResult.getFactura(), invoiceId, command.getCreatedBy());

        } catch (Exception e) {
            log.error("Error al generar factura: {}", e.getMessage(), e);
            throw new BusinessInvoiceException(DomainErrorInvoiceMessage.GENERAL_ERROR,
                    "Error al generar factura: " + e.getMessage());
        }
    }

    /**
     * Prepara todos los datos necesarios para generar una factura sin interactuar con la base de datos.
     * Esta fase no requiere transacción y puede tomar tiempo.
     */
    private InvoicePreparationResult prepareInvoiceData(GenerateInvoiceCommand command) {
        // 1. Obtener el emisor por su ID
        InvoiceIssuerDto issuerDto = invoiceIssuerService.getById(command.getIssuerId());

        // 2. Obtener y actualizar el secuencial para facturas del emisor
        String sequential = getNextSequentialFromIssuer(issuerDto, "01"); // 01 es el tipo de documento para facturas

        // 3. Buscar o crear el cliente (Customer) utilizando el servicio
        Customer customer = findOrUpdateCustomer(command);

        // 4. Crear el objeto Factura (modelo del SRI)
        Factura factura = createfactura(issuerDto, sequential, command.getDetalles(), customer, command.getPropina(),
                command.getPagos(), command.getInfoAdicional());

        // 5. Validar la factura
        if (validateInvoice(factura)) {
            log.error("Factura no válida, abortando generación.");
            throw new BusinessInvoiceException(DomainErrorInvoiceMessage.INVOICE_NOT_VALID,
                    "Factura no válida, verifique los datos.");
        }

        // 6. Convertir a DTO para la persistencia
        InvoiceDto invoiceDto = convertFacturaToInvoiceDto(factura, customer, issuerDto, command.getCreatedBy());
        log.info("InvoiceDto preparado - Cliente ID: {}, Cliente Núm. ID: {}",
                invoiceDto.getCustomer().getId(), invoiceDto.getCustomer().getIdentificationNumber());

        // 7. Devolver el resultado de la preparación
        return InvoicePreparationResult.builder()
                .invoiceDto(invoiceDto)
                .factura(factura)
                .accessKey(claveAcceso)
                .build();
    }

    /**
     * Método para generar documentos de forma asíncrona después de guardar la factura.
     * Este método podría implementarse usando @Async o un sistema de mensajería.
     */

    // @Async
    public void generateDocumentsAsync(Factura factura, UUID invoice, UUID userId) {
        try {
            log.info("Iniciando generación asíncrona de documentos para factura: {}", invoice);
            ByteArrayOutputStream xmlFactura = generateXmlAndInvoiceFile(factura);
            ByteArrayOutputStream pdfInvoice = generatePDFInvoice(factura);
            sendInvoiceSRI(xmlFactura, factura, invoice, userId);
            log.info("Documentos generados correctamente para factura: {}", invoice);
        } catch (Exception e) {
            log.error("Error en generación asíncrona de documentos: {}", e.getMessage(), e);
        }
    }


    private void sendInvoiceSRI(ByteArrayOutputStream xmlFactura, Factura factura, UUID invoice, UUID userId) {
        try {
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

            if (respuestaRecepcion.getEstado().equals(Estados.RECIBIDA))
                try {

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

    /**
     * Obtiene y actualiza el siguiente número secuencial para un tipo de documento de un emisor.
     * Este método actualiza la secuencia en la base de datos.
     *
     * @param issuer       DTO del emisor de facturas
     * @param documentType Tipo de documento (ej. "01" para facturas)
     * @return Secuencial formateado a 9 dígitos
     * @throws BusinessInvoiceException si no se encuentra una secuencia activa para el tipo de documento
     */
    private String getNextSequentialFromIssuer(InvoiceIssuerDto issuer, String documentType) {
        log.info("Obteniendo siguiente secuencial para emisor ID: {}, tipo documento: {}", issuer.getId(), documentType);

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

        // Actualizar la secuencia en la base de datos
        try {
            // Usar el servicio para actualizar la secuencia en la base de datos
            invoiceIssuerService.updateSequence(issuer.getId(), documentType, nextSequential);
            log.info("Secuencial actualizado correctamente en base de datos: {}", nextSequential);
        } catch (Exception e) {
            log.error("Error al actualizar secuencial en la base de datos: {}", e.getMessage(), e);
            throw new BusinessInvoiceException(
                    DomainErrorInvoiceMessage.GENERAL_ERROR,
                    "Error al actualizar secuencial en la base de datos: " + e.getMessage());
        }

        // También actualizar el objeto DTO local para consistencia
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
        // Crear detalles de la factura

        List<Factura.DetalleFactura> detallesList = new ArrayList<>();

        for (DetalleFacturaRequest detalleDTO : detallesDTO) {
            // Crear el builder del detalle con los valores básicos
            Factura.DetalleFactura.Builder detalleBuilder = new Factura.DetalleFactura.Builder(
                    detalleDTO.getCodigoPrincipal(),
                    detalleDTO.getDescripcion(),
                    detalleDTO.getCantidad(),
                    detalleDTO.getPrecioUnitario(),
                    Factura.Impuesto.IVA(detalleDTO.getTipoImpuesto(), detalleDTO.getPorcentajeImpuesto()))
                    .withUnidadMedida(detalleDTO.getUnidadMedida())
                    .withDescuento(detalleDTO.getDescuento());

            // Agregar impuesto ICE si es necesario (comentado actualmente)
            if (detalleDTO.getCodigoImpuestoICE() != null && detalleDTO.getPorcentajeImpuestoICE() != null) {
                detalleBuilder.withImpuestoICE(Factura.Impuesto.ICE(detalleDTO.getCodigoImpuestoICE(), detalleDTO.getPorcentajeImpuestoICE()));
            }

            // Construir el detalle final
            Factura.DetalleFactura detalle = detalleBuilder.build();


            detallesList.add(detalle);
        }

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
        Factura factura = new Factura.Builder(ruc, razonSocial, dirMatriz, correo, telefono, estab, ptoEmi, sequential, fechaEmision, detallesList)
                .withObligadoContabilidad(obligadoContabilidad)
                //.withAgenteRetencion("3867")
                //.withContribuyenteEspecial("7345")
                //   .withContribuyenteRimpe(Regimen.NEGOCIO_POPULAR)//Agregar a la empresa el tipo de régimen
                .withNombreComercial(issuer.getCommercialName())
                .withTipoIdentificacionComprador(customerIdentificationType)
                .withRazonSocialComprador(customer.getBusinessName())
                .withIdentificacionComprador(customer.getIdNumber())
                .withDireccionComprador(customer.getAddress())
                .withCorreoComprador(customer.getEmail()) // Corregido: usar email en lugar de duplicar la dirección
                .withTelefonoComprador(customer.getPhone())
                .withPropina(propina)
                .withPagos(payments)
                .withInfoAdicional(new ArrayList<>())
                .build();

        if (issuer.getRimpeRegime() != null && !issuer.getRimpeRegime().isEmpty()) {
            factura.setContribuyenteRimpe(issuer.getRimpeRegime());
        }

        if (issuer.getRetentionAgent() != null && !issuer.getRetentionAgent().isEmpty()) {
            factura.setAgenteRetencion(issuer.getRetentionAgent());
        }
        if (issuer.getSpecialTaxpayer() != null && !issuer.getSpecialTaxpayer().isEmpty()) {
            factura.setContribuyenteEspecial(issuer.getSpecialTaxpayer());
        }

        //  System.out.println(factura.getClaveAcceso());

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
                log.info("Cliente actualizado con ID: {} y número de identificación: {}",
                        customerDto.getId(), customerDto.getIdentificationNumber());
            } else {
                log.info("Cliente existente sin cambios, ID: {} y número de identificación: {}",
                        customerDto.getId(), customerDto.getIdentificationNumber());
            }
        } catch (Exception e) {
            // Cliente no encontrado, crearlo
            customerDto = CustomerDto.builder()
                    // No establecemos el ID aquí, dejamos que el servicio lo genere
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

            // Buscar el cliente recién creado para obtener todos sus datos
            customerDto = customerService.findById(customerId);
            log.info("Cliente creado correctamente con ID: {} y número de identificación: {}",
                    customerId, customerDto.getIdentificationNumber());
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

    /**
     * Convierte un objeto Factura creado por el API de facturación a un objeto InvoiceDto
     * utilizado por el sistema para guardar en la base de datos.
     *
     * @param factura   Objeto Factura generado
     * @param customer  Cliente asociado a la factura
     * @param issuerDto Emisor asociado a la factura
     * @param createdBy ID del usuario que crea la factura
     * @return InvoiceDto con los datos de la factura
     */
    private InvoiceDto convertFacturaToInvoiceDto(Factura factura, Customer customer, InvoiceIssuerDto issuerDto, UUID createdBy) {
        // Crear el InvoiceDto básico
        claveAcceso = factura.getClaveAcceso();
        System.out.println("Clave de acceso: " + claveAcceso);
        InvoiceDto invoiceDto = InvoiceDto.builder()
                .id(UUID.randomUUID())
                .issuerId(issuerDto.getId())
                .documentNumber(factura.getEstab() + "-" + factura.getPtoEmi() + "-" + factura.getSecuencial())
                .sequential(factura.getSecuencial())
                .accessKey(claveAcceso)
                .emissionDate(LocalDateTime.now()) // Fecha de emisión actual
                // Agregar la guía de remisión si existe en el objeto factura
                .remissionGuide(null) // Se debe actualizar si la factura incluye guía de remisión
                .subtotal(factura.getTotalSinImpuestos())
                .discount(factura.getTotalDescuento())
                .taxAmount(calculateTotalTaxAmount(factura))
                .totalAmount(factura.getImporteTotal())
                .tip(factura.getPropina())
                .status(InvoiceStatus.DRAFT) // Estado inicial DRAFT (borrador)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy(createdBy)
                .updatedBy(createdBy) // Inicialmente, createdBy y updatedBy son iguales
                .build();

        // Asignar el emisor
        DigitalCertificateDTO.IssuerDTO issuerDTO = DigitalCertificateDTO.IssuerDTO.builder()
                .id(issuerDto.getId())
                .businessName(issuerDto.getBusinessName())
                .ruc(issuerDto.getRuc())
                .build();
        invoiceDto.setIssuer(issuerDTO);

        // Asignar el cliente
        CustomerDto customerDto = CustomerDto.builder()
                .id(customer.getId())
                .identificationNumber(customer.getIdNumber())
                .identificationType(customer.getIdType())
                .businessName(customer.getBusinessName())
                .address(customer.getAddress())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhone())
                .isActive(customer.getIsActive())
                .build();
        invoiceDto.setCustomer(customerDto);

        // Mapear detalles de factura
        List<InvoiceDetailDto> detailDtos = new ArrayList<>();
        if (factura.getDetalles() != null) {
            for (int i = 0; i < factura.getDetalles().size(); i++) {
                Factura.DetalleFactura detalle = factura.getDetalles().get(i);

                // Crear lista de impuestos para este detalle
                List<InvoiceDetailTaxDto> detailTaxes = new ArrayList<>();
                BigDecimal totalWithTax = BigDecimal.ZERO;
                if (detalle.getImpuestos() != null) {
                    for (Factura.Impuesto impuesto : detalle.getImpuestos()) {
                        InvoiceDetailTaxDto taxDto = InvoiceDetailTaxDto.builder()
                                .id(UUID.randomUUID())
                                .code(impuesto.getCodigo())
                                .percentageCode(impuesto.getCodigoPorcentaje())
                                .rate(impuesto.getTarifa())
                                .taxableBase(impuesto.getBaseImponible())
                                .value(impuesto.getValor())
                                .build();
                        detailTaxes.add(taxDto);
                        totalWithTax = totalWithTax.add(impuesto.getValor());
                    }
                }

                // Calcular el subtotal con impuestos (precio total + impuestos)

                InvoiceDetailDto detailDto = InvoiceDetailDto.builder()
                        .id(UUID.randomUUID())
                        .lineNumber(i + 1) // Número de línea secuencial empezando desde 1
                        .mainCode(detalle.getCodigoPrincipal())
                        .description(detalle.getDescripcion())
                        .quantity(detalle.getCantidad())
                        .unitPrice(detalle.getPrecioUnitario())
                        .discount(detalle.getDescuento())
                        .subtotal(detalle.getPrecioTotalSinImpuesto())
                        .auxiliaryCode(detalle.getCodigoAuxiliar())
                        .unitOfMeasure(detalle.getUnidadMedida())
                        .taxes(detailTaxes) // Agregar la lista de impuestos
                        .totalWithTax(totalWithTax) // Total con impuestos
                        .build();
                detailDtos.add(detailDto);
            }
        }
        invoiceDto.setDetails(detailDtos);

        // Mapear pagos
        List<InvoicePaymentDto> paymentDtos = new ArrayList<>();
        if (factura.getPagos() != null) {
            for (Factura.Pago pago : factura.getPagos()) {
                InvoicePaymentDto paymentDto = InvoicePaymentDto.builder()
                        .id(UUID.randomUUID())
                        .paymentType(pago.getFormaPago())
                        .amount(pago.getTotal())
                        .reference(pago.getDescripcion()) // Usar descripción como referencia
                        .unidadTiempo(pago.getUnidadTiempo())
                        .plazo(pago.getPlazo())
                        .build();

                paymentDtos.add(paymentDto);
            }
        }
        invoiceDto.setPayments(paymentDtos);

        // Mapear campos adicionales
        List<InvoiceAdditionalFieldDto> additionalFieldDtos = new ArrayList<>();
        if (factura.getInfoAdicional() != null) {
            for (ComprobanteBase.CampoAdicional campo : factura.getInfoAdicional()) {
                InvoiceAdditionalFieldDto fieldDto = InvoiceAdditionalFieldDto.builder()
                        .id(UUID.randomUUID())
                        .name(campo.getNombre())
                        .value(campo.getValor())
                        .build();

                additionalFieldDtos.add(fieldDto);
            }
        }
        invoiceDto.setAdditionalFields(additionalFieldDtos);

        // Mapear impuestos (si existe la estructura en Factura)
        List<InvoiceTaxDto> taxDtos = new ArrayList<>();
        if (factura.getTotalConImpuestos() != null) {
            for (Factura.Impuesto impuesto : factura.getTotalConImpuestos()) {
                InvoiceTaxDto taxDto = InvoiceTaxDto.builder()
                        .id(UUID.randomUUID())
                        .code(impuesto.getCodigo())
                        .description(impuesto.getCodigoPorcentaje())
                        .rate(impuesto.getTarifa())
                        .baseAmount(impuesto.getBaseImponible())
                        .taxAmount(impuesto.getValor())
                        .build();

                taxDtos.add(taxDto);
            }
        }
        invoiceDto.setTaxes(taxDtos);

        // El emisor ya fue establecido arriba, no es necesario volver a establecerlo

        return invoiceDto;
    }

    /**
     * Calcula el monto total de impuestos de una factura sumando todos los valores de impuestos
     *
     * @param factura Factura con impuestos
     * @return Monto total de impuestos
     */
    private BigDecimal calculateTotalTaxAmount(Factura factura) {
        BigDecimal totalTaxes = BigDecimal.ZERO;

        if (factura.getTotalConImpuestos() != null) {
            for (Factura.Impuesto impuesto : factura.getTotalConImpuestos()) {
                totalTaxes = totalTaxes.add(impuesto.getValor());
            }
        }

        return totalTaxes;
    }

}
