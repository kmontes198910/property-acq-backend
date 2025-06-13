package com.kynsoft.invoiceservice.infrastructure.mapper;

import com.kynsoft.invoiceservice.domain.dto.InvoiceIssuerDto;
import com.kynsoft.invoiceservice.domain.service.IInvoiceIssuerService;
import com.kynsoft.invoiceservice.infrastructure.entities.Invoice;
import com.kynsoft.invoiceservice.infrastructure.job.ProcessInvoice;
import com.kynsoft.invoiceservice.util.CredentialUtil;
import ec.e.facturacion.sri.constante.Regimen;
import ec.e.facturacion.sri.modelo.ComprobanteBase;
import ec.e.facturacion.sri.modelo.Factura;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Clase encargada de mapear los datos de Invoice a ProcessInvoice y Factura
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MapperInvoice {

    private final IInvoiceIssuerService invoiceIssuerService;
    private final CredentialUtil credentialUtil;

    /**
     * Convierte un objeto Invoice a un objeto ProcessInvoice
     * @param invoice Entidad Invoice con los datos de la factura
     * @return ProcessInvoice con la factura formateada y los datos del certificado
     */
    public ProcessInvoice convertToFactura(Invoice invoice) {
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
        
        // Obtener los datos del emisor
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

    /**
     * Extrae el establecimiento del número de documento
     * @param documentNumber Número de documento en formato XXX-XXX-XXXXXXXXX
     * @return Código de establecimiento
     */
    private String extraerEstab(String documentNumber) {
        if (documentNumber != null && documentNumber.contains("-")) {
            String[] parts = documentNumber.split("-");
            if (parts.length > 0) return parts[0];
        }
        return "001";
    }

    /**
     * Extrae el punto de emisión del número de documento
     * @param documentNumber Número de documento en formato XXX-XXX-XXXXXXXXX
     * @return Código de punto de emisión
     */
    private String extraerPtoEmi(String documentNumber) {
        if (documentNumber != null && documentNumber.contains("-")) {
            String[] parts = documentNumber.split("-");
            if (parts.length > 1) return parts[1];
        }
        return "001";
    }
}
