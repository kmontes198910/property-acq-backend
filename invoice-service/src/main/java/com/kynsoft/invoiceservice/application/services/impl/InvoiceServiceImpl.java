package com.kynsoft.invoiceservice.application.services.impl;

import com.kynsoft.invoiceservice.application.services.InvoiceService;
import com.kynsoft.invoiceservice.dto.CampoAdicionalDTO;
import com.kynsoft.invoiceservice.dto.DetalleFacturaDTO;
import com.kynsoft.invoiceservice.dto.FacturaRequestDTO;
import com.kynsoft.invoiceservice.dto.FacturaResponseDTO;
import com.kynsoft.invoiceservice.dto.PagoDTO;
import com.kynsoft.invoiceservice.infrastructure.entities.Customer;
import com.kynsoft.invoiceservice.infrastructure.entities.Invoice;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceDetail;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceIssuer;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceStatus;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceAdditionalField;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoicePayment;
import com.kynsoft.invoiceservice.infrastructure.repositories.CustomerRepository;
import com.kynsoft.invoiceservice.infrastructure.repositories.InvoiceIssuerRepository;
import com.kynsoft.invoiceservice.infrastructure.repositories.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final InvoiceIssuerRepository invoiceIssuerRepository;

    @Override
    @Transactional
    public FacturaResponseDTO generateInvoice(FacturaRequestDTO request) {
        try {
            // 1. Obtener el emisor activo (InvoiceIssuer)
            InvoiceIssuer issuer = invoiceIssuerRepository.findFirstByActiveTrue()
                    .orElseThrow(() -> new RuntimeException("No se encontró un emisor de facturas activo en el sistema"));

            // 2. Buscar o crear el cliente (Customer)
            Customer customer = findOrUpdateCustomer(request);

            // 3. Generar el secuencial si no se proporcionó uno
            String sequential = request.getSecuencial();
            if (sequential == null || sequential.isEmpty()) {
                sequential = generateNextSequential();
            }

            // 4. Crear el documento con el formato correcto
            String documentNumber = String.format("%s-%s-%s", 
                    issuer.getEstablishment(), 
                    issuer.getEmissionPoint(), 
                    sequential);

            // 5. Preparar los valores totales
            BigDecimal subtotal = calculateSubtotal(request.getDetalles());
            BigDecimal taxAmount = calculateTaxes(request.getDetalles());
            BigDecimal discount = calculateDiscounts(request.getDetalles());
            BigDecimal totalAmount = subtotal.add(taxAmount).subtract(discount);
            if (request.getPropina() != null) {
                totalAmount = totalAmount.add(request.getPropina());
            }

            // 6. Crear la factura
            Invoice invoice = Invoice.builder()
                    .documentNumber(documentNumber)
                    .sequential(sequential)
                    .emissionDate(LocalDateTime.now())
                    .subtotal(subtotal)
                    .discount(discount)
                    .taxAmount(taxAmount)
                    .totalAmount(totalAmount)
                    .tip(request.getPropina())
                    .status(InvoiceStatus.DRAFT)
                    .issuer(issuer)
                    .customer(customer)
                    .createdAt(LocalDateTime.now())
                    .build();

            // 7. Agregar detalles de la factura
            addInvoiceDetails(invoice, request.getDetalles());

            // 8. Agregar pagos de la factura
            addInvoicePayments(invoice, request.getPagos());

            // 9. Agregar campos adicionales
            addInvoiceAdditionalFields(invoice, request.getInfoAdicional());

            // 10. Guardar la factura
            Invoice savedInvoice = invoiceRepository.save(invoice);

            // 11. Generar respuesta
            return FacturaResponseDTO.builder()
                    .mensaje("Factura generada correctamente")
                    .estado(savedInvoice.getStatus().toString())
                    .claveAcceso(savedInvoice.getAccessKey())
                    .build();

        } catch (Exception e) {
            log.error("Error al generar factura: {}", e.getMessage(), e);
            return FacturaResponseDTO.builder()
                    .mensaje("Error al generar factura: " + e.getMessage())
                    .estado("ERROR")
                    .build();
        }
    }

    @Override
    public String generateNextSequential() {
        Optional<String> lastSequential = invoiceRepository.findLastSequential();
        
        // Si no hay facturas previas, empezar desde 1
        if (lastSequential.isEmpty()) {
            return String.format("%09d", 1); // 000000001
        }
        
        // Convertir el último secuencial a número e incrementar
        try {
            int lastNumber = Integer.parseInt(lastSequential.get());
            return String.format("%09d", lastNumber + 1);
        } catch (NumberFormatException e) {
            log.error("Error al procesar el último secuencial: {}", e.getMessage());
            return String.format("%09d", 1);
        }
    }

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

    private BigDecimal calculateSubtotal(List<DetalleFacturaDTO> detalles) {
        return detalles.stream()
                .map(detalle -> detalle.getPrecioUnitario().multiply(detalle.getCantidad()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTaxes(List<DetalleFacturaDTO> detalles) {
        return detalles.stream()
                .map(detalle -> {
                    BigDecimal baseImponible = detalle.getPrecioUnitario().multiply(detalle.getCantidad());
                    if (detalle.getDescuento() != null) {
                        baseImponible = baseImponible.subtract(detalle.getDescuento());
                    }
                    // Calcular IVA
                    BigDecimal ivaAmount = BigDecimal.ZERO;
                    if (detalle.getPorcentajeImpuesto() != null) {
                        ivaAmount = baseImponible.multiply(detalle.getPorcentajeImpuesto())
                                .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                    }
                    // Calcular ICE si existe
                    BigDecimal iceAmount = BigDecimal.ZERO;
                    if (detalle.getPorcentajeImpuestoICE() != null) {
                        iceAmount = baseImponible.multiply(detalle.getPorcentajeImpuestoICE())
                                .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                    }
                    return ivaAmount.add(iceAmount);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateDiscounts(List<DetalleFacturaDTO> detalles) {
        return detalles.stream()
                .map(DetalleFacturaDTO::getDescuento)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void addInvoiceDetails(Invoice invoice, List<DetalleFacturaDTO> detalles) {
        int lineNumber = 1;
        for (DetalleFacturaDTO detalle : detalles) {
            BigDecimal subtotal = detalle.getPrecioUnitario().multiply(detalle.getCantidad());
            BigDecimal totalWithTax = subtotal;
            
            BigDecimal ivaAmount = BigDecimal.ZERO;
            BigDecimal ivaRate = BigDecimal.ZERO;
            String ivaCode = null;
            
            if (detalle.getPorcentajeImpuesto() != null) {
                ivaCode = detalle.getTipoImpuesto();
                ivaRate = detalle.getPorcentajeImpuesto();
                ivaAmount = subtotal.multiply(ivaRate).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                totalWithTax = totalWithTax.add(ivaAmount);
            }
            
            BigDecimal iceAmount = BigDecimal.ZERO;
            BigDecimal iceRate = BigDecimal.ZERO;
            String iceCode = null;
            
            if (detalle.getPorcentajeImpuestoICE() != null) {
                iceCode = detalle.getCodigoImpuestoICE();
                iceRate = detalle.getPorcentajeImpuestoICE();
                iceAmount = subtotal.multiply(iceRate).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                totalWithTax = totalWithTax.add(iceAmount);
            }
            
            if (detalle.getDescuento() != null) {
                subtotal = subtotal.subtract(detalle.getDescuento());
                totalWithTax = totalWithTax.subtract(detalle.getDescuento());
            }
            
            InvoiceDetail invoiceDetail = InvoiceDetail.builder()
                    .lineNumber(lineNumber++)
                    .mainCode(detalle.getCodigoPrincipal())
                    .auxiliaryCode(detalle.getCodigoAuxiliar())
                    .description(detalle.getDescripcion())
                    .quantity(detalle.getCantidad())
                    .unitOfMeasure(detalle.getUnidadMedida())
                    .unitPrice(detalle.getPrecioUnitario())
                    .discount(detalle.getDescuento())
                    .subtotal(subtotal)
                    .ivaCode(ivaCode)
                    .ivaRate(ivaRate)
                    .ivaAmount(ivaAmount)
                    .iceCode(iceCode)
                    .iceRate(iceRate)
                    .iceAmount(iceAmount)
                    .totalWithTax(totalWithTax)
                    .invoice(invoice)
                    .build();
                    
            invoice.addDetail(invoiceDetail);
        }
    }

    private void addInvoicePayments(Invoice invoice, List<PagoDTO> pagos) {
        if (pagos == null || pagos.isEmpty()) {
            // Si no hay pagos especificados, crear un pago por defecto
            InvoicePayment payment = InvoicePayment.builder()
                    .paymentMethod("01") // Sin utilización del sistema financiero
                    .amount(invoice.getTotalAmount())
                    .invoice(invoice)
                    .build();
                    
            invoice.addPayment(payment);
        } else {
            for (PagoDTO pago : pagos) {
                InvoicePayment payment = InvoicePayment.builder()
                        .paymentMethod(pago.getFormaPago())
                        // La propiedad description no existe en InvoicePayment
                        // Usar paymentMethodName para almacenar la descripción
                        .paymentMethodName(pago.getDescripcion())
                        .amount(pago.getTotal() != null ? pago.getTotal() : invoice.getTotalAmount())
                        .timeValue(pago.getPlazo())
                        .timeUnit(pago.getUnidadTiempo())
                        .invoice(invoice)
                        .build();
                        
                invoice.addPayment(payment);
            }
        }
    }

    private void addInvoiceAdditionalFields(Invoice invoice, List<CampoAdicionalDTO> campos) {
        if (campos != null && !campos.isEmpty()) {
            for (CampoAdicionalDTO campo : campos) {
                InvoiceAdditionalField field = InvoiceAdditionalField.builder()
                        .name(campo.getNombre())
                        .value(campo.getValor())
                        .invoice(invoice)
                        .build();
                        
                invoice.addAdditionalField(field);
            }
        }
    }
}