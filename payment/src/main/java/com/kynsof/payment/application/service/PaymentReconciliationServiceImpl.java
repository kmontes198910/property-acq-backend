package com.kynsof.payment.application.service;

import com.kynsof.payment.application.query.PaymentReconciliationDetails.PaymentReconciliationDetailsResponse;
import com.kynsof.payment.application.query.PaymentReconciliationHeader.PaymentReconciliationHeaderResponse;
import com.kynsof.payment.domain.service.IPaymentReconciliationService;
import com.kynsof.payment.infrastructure.entity.Billing;
import com.kynsof.payment.infrastructure.entity.Business;
import com.kynsof.payment.infrastructure.entity.PaymentReconciliationDetail;
import com.kynsof.payment.infrastructure.entity.PaymentReconciliationHeader;
import com.kynsof.payment.infrastructure.repositories.command.PaymentReconciliationDetailRepository;
import com.kynsof.payment.infrastructure.repositories.command.PaymentReconciliationHeaderRepository;
import com.kynsof.payment.infrastructure.repositories.query.BusinessReadDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.query.GroupPaymentDetailReadDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.query.PaymentReconciliationDetailReadDataJPARepository;
import com.kynsof.payment.infrastructure.repositories.query.PaymentReconciliationHeaderReadDataJPARepository;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentReconciliationServiceImpl implements IPaymentReconciliationService {
    private final PaymentReconciliationHeaderReadDataJPARepository paymentReconciliationHeaderReadDataJPARepository;
    private final GroupPaymentDetailReadDataJPARepository paymentDetailRepository;
    private final PaymentReconciliationHeaderRepository headerRepository;
    private final PaymentReconciliationDetailReadDataJPARepository paymentReconciliationDetailReadDataJPARepository;

    private final PaymentReconciliationDetailRepository detailRepository;
    private final BusinessReadDataJPARepository businessRepository;

    public PaymentReconciliationServiceImpl(PaymentReconciliationHeaderReadDataJPARepository paymentReconciliationHeaderReadDataJPARepository, GroupPaymentDetailReadDataJPARepository paymentDetailReadDataJPARepository, PaymentReconciliationHeaderRepository headerRepository, PaymentReconciliationDetailReadDataJPARepository paymentReconciliationDetailReadDataJPARepository, PaymentReconciliationDetailRepository detailRepository, BusinessReadDataJPARepository businessRepository) {
        this.paymentReconciliationHeaderReadDataJPARepository = paymentReconciliationHeaderReadDataJPARepository;
        this.paymentDetailRepository = paymentDetailReadDataJPARepository;
        this.headerRepository = headerRepository;
        this.paymentReconciliationDetailReadDataJPARepository = paymentReconciliationDetailReadDataJPARepository;
        this.detailRepository = detailRepository;
        this.businessRepository = businessRepository;
    }


    @Override
    public PaymentReconciliationHeader reconcilePayments(LocalDateTime startDate, LocalDateTime endDate, UUID businessId, UUID userI, String userFullName) {
        // Validar si la empresa existe
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new RuntimeException("Business not found with ID: " + businessId));

        // Verificar si ya existe un cuadre contable para la fecha y empresa
        Long existingCount = headerRepository.countByDateAndBusiness(startDate, businessId);
        if (existingCount > 0) {
            throw new RuntimeException("Ya existe un cuadre contable para esta fecha y empresa.");
        }

        // Obtener la cantidad de `GroupPayment` distintos en el rango de fechas
        Long totalPayments = paymentDetailRepository.countDistinctGroupPayments(startDate, endDate, businessId);

        // Obtener los pagos agrupados por servicio para la empresa específica
        List<Object[]> results = paymentDetailRepository.findGroupedPaymentsByServiceAndBusiness(startDate, endDate, businessId);

        // Verificar si results está vacío (no hay pagos aprobados)
        if (results.isEmpty()) {
            return new PaymentReconciliationHeader(startDate, endDate, 0L, 0.0, business, userI, userFullName, 0.0,0.0,0.0);
        }

        // Calcular el total recaudado
        double totalRevenue = results.stream().mapToDouble(row -> ((Number) row[2]).doubleValue()).sum();
        double totalPlacetoPay = results.stream().mapToDouble(row -> ((Number) row[3]).doubleValue()).sum();
        double totalCash = results.stream().mapToDouble(row -> ((Number) row[4]).doubleValue()).sum();
        double totalTransfer = results.stream().mapToDouble(row -> ((Number) row[5]).doubleValue()).sum();

        // Crear y guardar la cabecera de conciliación
        PaymentReconciliationHeader header = new PaymentReconciliationHeader(startDate, endDate, totalPayments, totalRevenue, business,userI, userFullName, totalPlacetoPay, totalCash, totalTransfer);
        headerRepository.save(header);

        // Crear y guardar los detalles de la conciliación
        List<PaymentReconciliationDetail> details = results.stream()
                .map(row -> {
                    String serviceCode = (String) row[0];
                    int serviceCount = ((Number) row[1]).intValue();
                    double totalAmount = ((Number) row[2]).doubleValue();
                    double placetopayAmount = ((Number) row[3]).doubleValue();
                    double cashAmount = ((Number) row[4]).doubleValue();
                    double transferAmount = ((Number) row[5]).doubleValue();

                    // Obtener todas las facturas con el mismo código de servicio
                    List<Billing> billings = paymentDetailRepository.findBillingByServiceCode(serviceCode);

                    // Tomar solo la primera descripción disponible
                    String description = billings.isEmpty() ? "Descripción no disponible" : billings.get(0).getDescription();

                    return new PaymentReconciliationDetail(
                            header,
                            serviceCode,
                            description,
                            serviceCount,
                            totalAmount,
                            placetopayAmount,
                            cashAmount,
                            transferAmount
                    );
                })
                .collect(Collectors.toList());

        detailRepository.saveAll(details); // Guardar los detalles
        return header;
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Billing> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<PaymentReconciliationHeader> data = this.paymentReconciliationHeaderReadDataJPARepository.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }


    @Override
    public PaginatedResponse searchReconciliationDetail(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Billing> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<PaymentReconciliationDetail> data = this.paymentReconciliationDetailReadDataJPARepository.findAll(specifications, pageable);
        return getPaginatedDetailResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<PaymentReconciliationHeader> data) {
        List<PaymentReconciliationHeaderResponse> patients = new ArrayList<>();
        for (PaymentReconciliationHeader o : data.getContent()) {
            patients.add(new PaymentReconciliationHeaderResponse(
                    o.getId(),
                    o.getStartDate(),
                    o.getEndDate(),
                    o.getTotalPayments(),
                    o.getTotalRevenue(),
                    o.getCreatedAt(),
                    o.getUserSystemId(),
                    o.getUserSystemFullName(),
                    o.getTotalPlacetoPay(),
                    o.getTotalCash(),
                    o.getTotalTransfer()
                    ));
        }
        return new PaginatedResponse(patients, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    private PaginatedResponse getPaginatedDetailResponse(Page<PaymentReconciliationDetail> data) {
        List<PaymentReconciliationDetailsResponse> patients = new ArrayList<>();
        for (PaymentReconciliationDetail o : data.getContent()) {
            patients.add(new PaymentReconciliationDetailsResponse(
                    o.getId(),
                    o.getServiceCode(),
                    o.getDescription(),
                    o.getTotalAmount(),
                    o.getPlacetopayAmount(),
                    o.getCashAmount(),
                    o.getTransferAmount(),
                    o.getServiceCount(),
                    o.getCreatedAt()

            ));
        }
        return new PaginatedResponse(patients, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }
}
