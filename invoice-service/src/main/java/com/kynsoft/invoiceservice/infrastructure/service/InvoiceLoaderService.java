//package com.kynsoft.invoiceservice.infrastructure.service;
//
//import com.kynsoft.invoiceservice.infrastructure.entities.Invoice;
//import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceDetail;
//import com.kynsoft.invoiceservice.infrastructure.repository.query.InvoiceDetailRepository;
//import com.kynsoft.invoiceservice.infrastructure.repository.query.InvoiceRepository;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.UUID;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
///**
// * Servicio para manejar la carga optimizada de facturas con sus relaciones
// * para evitar el problema de MultipleBagFetchException
// */
//@Service
//public class InvoiceLoaderService {
//
//    private final InvoiceRepository invoiceRepository;
//    private final InvoiceDetailRepository invoiceDetailRepository;
//
//    public InvoiceLoaderService(
//            InvoiceRepository invoiceRepository,
//            InvoiceDetailRepository invoiceDetailRepository) {
//        this.invoiceRepository = invoiceRepository;
//        this.invoiceDetailRepository = invoiceDetailRepository;
//    }
//
//    /**
//     * Carga una factura completa con todas sus relaciones de forma optimizada
//     * para evitar MultipleBagFetchException
//     *
//     * @param invoiceId ID de la factura a cargar
//     * @return Factura completa con todas sus relaciones
//     */
//    @Transactional(readOnly = true)
//    public Invoice loadCompleteInvoice(UUID invoiceId) {
//        // Cargar la factura básica con sus detalles
//        Invoice invoice = invoiceRepository.findById(invoiceId).orElse(null);
//
//        if (invoice == null) {
//            return null;
//        }
//
//        // Cargar los detalles con sus impuestos
//        List<InvoiceDetail> detailsWithTaxes = invoiceDetailRepository.findByInvoiceIdWithTaxes(invoiceId);
//
//        // Mapeo para actualizar los detalles
//        Map<UUID, InvoiceDetail> detailMap = detailsWithTaxes.stream()
//                .collect(Collectors.toMap(InvoiceDetail::getId, Function.identity()));
//
//        // Actualizar los detalles en la factura con los que tienen los impuestos cargados
//        invoice.getDetails().forEach(detail -> {
//            InvoiceDetail detailWithTaxes = detailMap.get(detail.getId());
//            if (detailWithTaxes != null) {
//                detail.setTaxes(detailWithTaxes.getTaxes());
//                detail.setAdditionalInfo(detailWithTaxes.getAdditionalInfo());
//            }
//        });
//
//        return invoice;
//    }
//
//    /**
//     * Carga una lista de facturas con todas sus relaciones de forma optimizada
//     *
//     * @param invoiceIds Lista de IDs de facturas
//     * @return Lista de facturas con todas sus relaciones
//     */
//    @Transactional(readOnly = true)
//    public List<Invoice> loadCompleteInvoices(List<UUID> invoiceIds) {
//        return invoiceIds.stream()
//                .map(this::loadCompleteInvoice)
//                .filter(invoice -> invoice != null)
//                .collect(Collectors.toList());
//    }
//}
