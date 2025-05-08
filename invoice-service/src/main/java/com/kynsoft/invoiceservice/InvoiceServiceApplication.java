package com.kynsoft.invoiceservice;

import ec.e.facturacion.sri.constante.Ambiente;
import ec.e.facturacion.sri.constante.Estados;
import ec.e.facturacion.sri.constante.Regimen;
import ec.e.facturacion.sri.modelo.ComprobanteBase;
import ec.e.facturacion.sri.modelo.Factura;
import ec.e.facturacion.sri.pdf.generador.FacturaPDFGenerador;
import ec.e.facturacion.sri.util.*;
import ec.e.facturacion.sri.ws.autorizacion.prueba.RespuestaComprobante;
import ec.e.facturacion.sri.ws.recepcion.prueba.RespuestaSolicitud;
import ec.e.facturacion.sri.ws.soap.servicio.SRIAutorizacionServicio;
import ec.e.facturacion.sri.ws.soap.servicio.SRIRecepcionServicio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootApplication
public class InvoiceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvoiceServiceApplication.class, args);
        System.out.println("Invoice Service is running...");
// Crear una factura de ejemplo
        Factura factura = crearFacturaEjemplo();

        try {
            // Validar la factura
            List<MensajeSRI> mensajes = factura.validar();
            if (!mensajes.isEmpty()) {
                SRIImprimirValidacionUtil.imprimirResultadoValidacion(mensajes);
                return;
            }

            // Generar el XML
            byte [] p12Bytes = FileConverterUtil.p12FileToByteArray("/Users/keimermontes/Development/medinec/scheduled/invoice-service/libs/0961881992.p12");

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

        } catch (Exception e) {
            System.err.println("Error al generar o guardar la factura: " + e.getMessage());
            e.printStackTrace();
        }


    }


    private static Factura crearFacturaEjemplo() {
        // Información básica de la factura
        String ruc = "0961881992001";
        String razonSocial = "Keimer Montes Oliver";
        String estab = "001";
        String ptoEmi = "001";
        String secuencial = "000000092";
        String dirMatriz = "Fernández Salvador OE844 y AV Mariscal Sucre, Quito, Pichincha, Ecuador";
        String correo = "jarango71@gmail.com";
        String telefono = "+593 995230554";
        String fechaEmision = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Crear detalles de la factura
        List<Factura.DetalleFactura> detalles = List.of(
                new Factura.DetalleFactura.Builder("001", "Software development - APP FLUTTER", BigDecimal.valueOf(1),
                        BigDecimal.valueOf(5000), Factura.Impuesto.IVA("4", BigDecimal.valueOf(15))).withUnidadMedida("U")
                        .build(),

                new Factura.DetalleFactura.Builder("002", "Software development - Backend JAVA", BigDecimal.valueOf(1),
                        BigDecimal.valueOf(10000), Factura.Impuesto.IVA("5", BigDecimal.valueOf(5))).withUnidadMedida("U")
                        .build(),

                new Factura.DetalleFactura.Builder("003", "Software development - Backend PYTHON", BigDecimal.valueOf(1),
                        BigDecimal.valueOf(10000.00), Factura.Impuesto.IVA("4", BigDecimal.valueOf(15)))
                        .withImpuestoICE(Factura.Impuesto.ICE("3077", BigDecimal.valueOf(20))).withUnidadMedida("U")
                        .withDescuento(BigDecimal.valueOf(100.00)).build(),

                new Factura.DetalleFactura.Builder("004", "Soporte", BigDecimal.valueOf(1), BigDecimal.valueOf(5000),
                        Factura.Impuesto.IVA("5", BigDecimal.valueOf(5))).withUnidadMedida("U").build());

        // Pagos - 01SIN UTILIZACION DEL SISTEMA FINANCIERO y se quiera pagar en varios
        // pagos usando .withPagos(pagos)
        // List<Pago> pagos = List.of(
        // new Pago("01", BigDecimal.valueOf(17681.0), BigDecimal.valueOf(1), "MES"),
        // new Pago("01", BigDecimal.valueOf(17681.0), BigDecimal.valueOf(1), "MES"));
        // En el caso que sea un solo pago, no se especifica el valor a pagar, la
        // factura lo actualiza,
        // solo la forma de pago es obligatoria, plazo y unidadTiempo son opcionales
        // .withPago(new Pago("01"))
        // 01, SIN UTILIZACION DEL SISTEMA FINANCIERO, 3 meses de plazo
        // .withPago(new Pago("01", "SIN UTILIZACION DEL SISTEMA FINANCIERO", BigDecimal.valueOf(3), "MES"))

        // Información adicional
        // En el caso que dese mandar informacion adicional aparte de el correo y telefono del comprador
        // lo puede especificar de esta forma
        List<ComprobanteBase.CampoAdicional> infoAdicional = List.of(new ComprobanteBase.CampoAdicional("info1", "valor1"),
                new ComprobanteBase.CampoAdicional("info2", "valor2"));

        // Construir la factura
        return new Factura.Builder(ruc, razonSocial, dirMatriz, correo, telefono, estab, ptoEmi, secuencial, fechaEmision, detalles)
                .withNombreComercial("DYNOISE S.A.")
                .withObligadoContabilidad("NO")
                //.withAgenteRetencion("3867")
                //.withContribuyenteEspecial("7345")
                .withContribuyenteRimpe(Regimen.NEGOCIO_POPULAR)
                .withTipoIdentificacionComprador("04")
                .withRazonSocialComprador("KEIME MONTES OLIVER")
                .withIdentificacionComprador("1790012345001")
                .withDireccionComprador("456 AV. SECUNDARIA Y OTRA, QUITO")
                .withCorreoComprador("keimermo1989@gmail.com")
                .withTelefonoComprador("+593 995230554")
                .withPropina(BigDecimal.valueOf(100))
                .withPago(new Factura.Pago("01", "SIN UTILIZACION DEL SISTEMA FINANCIERO", BigDecimal.valueOf(1), "MES"))
                .withInfoAdicional(infoAdicional)
                .build();
    }
}
