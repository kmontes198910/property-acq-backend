package com.kynsoft.invoiceservice.infrastructure.job;

import com.kynsoft.invoiceservice.infrastructure.entities.Invoice;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceDetail;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceStatus;
import com.kynsoft.invoiceservice.infrastructure.repository.query.InvoiceDetailRepository;
import com.kynsoft.invoiceservice.infrastructure.repository.query.InvoiceRepository;
import com.kynsoft.invoiceservice.infrastructure.service.InvoiceLoaderService;
import ec.e.facturacion.sri.modelo.Factura;
import ec.e.facturacion.sri.modelo.ComprobanteBase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
@Component
public class DraftInvoiceJob {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceDetailRepository invoiceDetailRepository;
    private final InvoiceLoaderService invoiceLoaderService;

    public DraftInvoiceJob(
            InvoiceRepository invoiceRepository, 
            InvoiceDetailRepository invoiceDetailRepository,
            InvoiceLoaderService invoiceLoaderService) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceDetailRepository = invoiceDetailRepository;
        this.invoiceLoaderService = invoiceLoaderService;
    }

    // Ejecuta cada día a la medianoche
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void buscarFacturasEnBorrador() {
        // Primero obtenemos los IDs de facturas en estado DRAFT
        List<Invoice> facturasBorrador = invoiceRepository.findByStatus(InvoiceStatus.DRAFT);


        // Convertir cada Invoice a Factura
        List<ProcessInvoice> facturas = facturasBorrador.stream()
                .map(this::convertToFactura)
                .toList();
                
        System.out.println("Facturas en estado DRAFT encontradas: " + facturas.size());
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
        String fechaEmision = invoice.getEmissionDate() != null ? invoice.getEmissionDate().toLocalDate().toString() : "";

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

        // Pagos
        List<Factura.Pago> pagos = new ArrayList<>(invoice.getPayments().stream().map(p ->
                new Factura.Pago(
                        p.getPaymentMethod(),
                        p.getPaymentMethodName(),
                        p.getTimeValue() != null ? java.math.BigDecimal.valueOf(p.getTimeValue()) : null,
                        p.getTimeUnit()
                )
        ).toList());

        // Información adicional
        List<ComprobanteBase.CampoAdicional> infoAdicional = new ArrayList<>(invoice.getAdditionalFields().stream().map(f ->
                new ComprobanteBase.CampoAdicional(f.getName(), f.getValue())
        ).toList());

        // Construir la factura
        Factura.Builder builder = new Factura.Builder(
                ruc, razonSocial, dirMatriz, correo, telefono, estab, ptoEmi, secuencial, fechaEmision, detalles
        );

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
        return new ProcessInvoice(invoice.getId(), builder.build());
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
}
