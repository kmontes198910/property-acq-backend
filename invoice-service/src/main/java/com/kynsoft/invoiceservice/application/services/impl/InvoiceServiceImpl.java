package com.kynsoft.invoiceservice.application.services.impl;

import com.kynsoft.invoiceservice.application.command.invoice.generate.request.CampoAdicionalRequest;
import com.kynsoft.invoiceservice.application.command.invoice.generate.request.DetalleFacturaRequest;
import com.kynsoft.invoiceservice.application.command.invoice.generate.request.PagoRequest;
import com.kynsoft.invoiceservice.application.services.InvoiceService;
import com.kynsoft.invoiceservice.dto.FacturaRequestDTO;
import com.kynsoft.invoiceservice.dto.FacturaResponseDTO;
import com.kynsoft.invoiceservice.infrastructure.entities.Customer;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceIssuer;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceIssuingSequence;
import com.kynsoft.invoiceservice.infrastructure.repository.query.CustomerReadRepository;
import com.kynsoft.invoiceservice.infrastructure.repository.query.InvoiceIssuerRepository;
import com.kynsoft.invoiceservice.infrastructure.repository.query.InvoiceReadRepository;
import ec.e.facturacion.sri.modelo.ComprobanteBase;
import ec.e.facturacion.sri.modelo.Factura;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceReadRepository invoiceRepository;
    private final CustomerReadRepository customerRepository;
    private final InvoiceIssuerRepository invoiceIssuerRepository;

    @Override
    @Transactional
    public FacturaResponseDTO generateInvoice(FacturaRequestDTO request) {
        try {
            // 1. Obtener el emisor por su ID (o el emisor activo si no se proporcionó un ID)

            InvoiceIssuer issuer = invoiceIssuerRepository.findById(request.getIssuerId())
                    .orElseThrow(() -> new RuntimeException("No se encontró un emisor de facturas con el ID: " + request.getIssuerId()));


            // 2. Obtener y actualizar el secuencial para facturas del emisor
            String sequential = getNextSequentialFromIssuer(issuer, "01"); // 01 es el tipo de documento para facturas

            // 3. Buscar o crear el cliente (Customer)
            Customer customer = findOrUpdateCustomer(request);


            Factura factura = createfactura(issuer, sequential, request.getDetalles(), customer, request.getPropina(),
                    request.getPagos(), request.getInfoAdicional());


//            // 8. Crear la factura
//            Invoice invoice = Invoice.builder()
//                    .documentNumber(documentNumber)
//                    .sequential(sequential)
//                    .emissionDate(LocalDateTime.now())
//                    .subtotal(subtotal)
//                    .discount(discount)
//                    .taxAmount(taxAmount)
//                    .totalAmount(totalAmount)
//                    .tip(request.getPropina())
//                    .status(InvoiceStatus.DRAFT)
//                    .issuer(issuer)
//                    .customer(customer)
//                    .createdAt(LocalDateTime.now())
//                    .accessKey(accessKey)
//                    .build();

//            // 9. Agregar detalles de la factura
//            addInvoiceDetails(invoice, request.getDetalles());
//
//            // 10. Agregar pagos de la factura
//            addInvoicePayments(invoice, request.getPagos());
//
//            // 11. Agregar campos adicionales
//            addInvoiceAdditionalFields(invoice, request.getInfoAdicional());

            // 12. Guardar la factura
            //    Invoice savedInvoice = invoiceRepository.save(invoice);

            // 13. Generar respuesta
            return FacturaResponseDTO.builder()
                    .mensaje("Factura generada correctamente")
                    .estado(factura.getEstado())
                    .claveAcceso(factura.getClaveAcceso())
                    .build();


        } catch (Exception e) {
            log.error("Error al generar factura: {}", e.getMessage(), e);
            return FacturaResponseDTO.builder()
                    .mensaje("Error al generar factura: " + e.getMessage())
                    .estado("ERROR")
                    .build();
        }
    }

    @Transactional
    private Customer findOrUpdateCustomer(FacturaRequestDTO request) {
        Optional<Customer> existingCustomer = customerRepository
                .findByIdNumber(request.getIdentificacionComprador());

        if (existingCustomer.isPresent()) {
            // Actualizar el cliente con los nuevos datos si es necesario
            Customer customer = existingCustomer.get();
            // Solo actualizar si hay cambios
            if (!customer.getBusinessName().equals(request.getRazonSocialComprador()) ||
                !customer.getAddress().equals(request.getDireccionComprador()) ||
                !customer.getEmail().equals(request.getCorreoComprador()) ||
                !customer.getPhone().equals(request.getTelefonoComprador())) {

                customer.setBusinessName(request.getRazonSocialComprador());
                customer.setAddress(request.getDireccionComprador());
                customer.setEmail(request.getCorreoComprador());
                customer.setPhone(request.getTelefonoComprador());

                return customerRepository.save(customer);
            }
            return customer;
        } else {
            // Crear un nuevo cliente
            Customer newCustomer = Customer.builder()
                    .idNumber(request.getIdentificacionComprador())
                    .idType(request.getTipoIdentificacionComprador())
                    .businessName(request.getRazonSocialComprador())
                    .address(request.getDireccionComprador())
                    .email(request.getCorreoComprador())
                    .phone(request.getTelefonoComprador())
                    .build();

            return customerRepository.save(newCustomer);
        }
    }

    private String getNextSequentialFromIssuer(InvoiceIssuer issuer, String documentType) {
        // Buscar la secuencia para el tipo de documento específico
        Optional<InvoiceIssuingSequence> sequenceOpt = issuer.getSequences().stream()
                .filter(seq -> documentType.equals(seq.getDocumentType()) && Boolean.TRUE.equals(seq.getIsActive()))
                .findFirst();

        InvoiceIssuingSequence sequence;
        if (sequenceOpt.isPresent()) {
            sequence = sequenceOpt.get();
        } else {
            // Si no existe una secuencia para este tipo de documento, crear una nueva
            sequence = InvoiceIssuingSequence.builder()
                    .documentType(documentType)
                    .currentSequential(0L)
                    .isActive(true)
                    .invoiceIssuer(issuer)
                    .build();
            issuer.addSequence(sequence);
        }

        // Incrementar el secuencial
        Long nextSequential = sequence.getCurrentSequential() + 1;
        sequence.setCurrentSequential(nextSequential);
        sequence.setLastUsedDate(LocalDateTime.now());

        // Formatear a 9 dígitos
        return String.format("%09d", nextSequential);
    }

    private Factura createfactura(InvoiceIssuer issuer, String sequential, List<DetalleFacturaRequest> detallesDTO,
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
        // Construir la factura
        Factura factura = new Factura.Builder(ruc, razonSocial, dirMatriz, correo, telefono, estab, ptoEmi, sequential, fechaEmision, detalles)
                .withNombreComercial(razonSocial)
                .withObligadoContabilidad(obligadoContabilidad)
                .withAgenteRetencion(issuer.getRetentionAgent())//?
                .withContribuyenteEspecial(issuer.getSpecialTaxpayer())//?
                .withContribuyenteRimpe(issuer.getRimpeRegime())//?
                .withTipoIdentificacionComprador(customer.getIdType().getCode()) // Obtener el código ("04", "05", etc) en lugar del nombre del enumerado
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
}