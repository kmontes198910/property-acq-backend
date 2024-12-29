package com.kynsof.treatments.infrastructure.service;

import com.kynsof.treatments.domain.dto.enumDto.BillingStatus;
import com.kynsof.treatments.domain.dto.enumDto.GroupPaymentStatus;
import com.kynsof.treatments.domain.service.IGroupPaymentService;
import com.kynsof.treatments.infrastructure.entity.Billing;
import com.kynsof.treatments.infrastructure.entity.GroupPayment;
import com.kynsof.treatments.infrastructure.entity.PaymentDetail;
import com.kynsof.treatments.infrastructure.repositories.command.BillingWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.command.GroupPaymentWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.command.PaymentDetailWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.query.BillingReadDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.query.PaymentDetailReadDataJPARepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class GroupPaymentServiceImpl implements IGroupPaymentService {

    private final BillingReadDataJPARepository repositoryQuery;

    private final BillingWriteDataJPARepository billingWriteDataJPARepository;
    private final GroupPaymentWriteDataJPARepository groupPaymentWriteDataJPARepository;
    private final PaymentDetailWriteDataJPARepository paymentDetailWriteDataJPARepository;
    private final PaymentDetailReadDataJPARepository paymentDetailReadDataJPARepository;

    public GroupPaymentServiceImpl(BillingReadDataJPARepository repositoryQuery, BillingWriteDataJPARepository repositoryCommand, GroupPaymentWriteDataJPARepository groupPaymentWriteDataJPARepository, PaymentDetailWriteDataJPARepository paymentDetailWriteDataJPARepository, PaymentDetailReadDataJPARepository paymentDetailReadDataJPARepository) {
        this.repositoryQuery = repositoryQuery;
        this.billingWriteDataJPARepository = repositoryCommand;
        this.groupPaymentWriteDataJPARepository = groupPaymentWriteDataJPARepository;
        this.paymentDetailWriteDataJPARepository = paymentDetailWriteDataJPARepository;
        this.paymentDetailReadDataJPARepository = paymentDetailReadDataJPARepository;
    }

    @Transactional
    @Override
    public void delete(UUID groupPaymentId) {
        // Buscar el grupo de pago
        GroupPayment groupPayment = groupPaymentWriteDataJPARepository.findById(groupPaymentId)
                .orElseThrow(() -> new IllegalArgumentException("GroupPayment not found with id: " + groupPaymentId));

        // Obtener los detalles del pago asociados al grupo
        List<PaymentDetail> paymentDetails = paymentDetailReadDataJPARepository.findByGroupPayment(groupPayment);

        // Cambiar el estado de las facturas asociadas
        for (PaymentDetail paymentDetail : paymentDetails) {
            Billing billing = paymentDetail.getBilling();
            billing.setStatus(BillingStatus.PENDING); // Cambiar el estado a PENDING u otro apropiado
            billingWriteDataJPARepository.save(billing); // Guardar el cambio
        }

        // Eliminar los detalles del pago asociados
        paymentDetailWriteDataJPARepository.deleteAll(paymentDetails);

        // Eliminar el grupo de pago
        groupPaymentWriteDataJPARepository.delete(groupPayment);
    }

    @Override
    @Transactional
    public UUID createGroupPayment(List<UUID> billingIds) {
        List<Billing> billings = this.repositoryQuery.findAllById(billingIds);
        GroupPayment groupPayment = new GroupPayment("", LocalDateTime.now(), "",
                "", "");
        groupPayment.setStatus(GroupPaymentStatus.PENDING_PAID);
        groupPayment = this.groupPaymentWriteDataJPARepository.save(groupPayment);

        for (Billing billing : billings) {
            PaymentDetail paymentDetail = new PaymentDetail(groupPayment, billing, billing.getCost());
            this.paymentDetailWriteDataJPARepository.save(paymentDetail);

            billing.setStatus(BillingStatus.PENDING_PAID);
            this.billingWriteDataJPARepository.save(billing);
        }

        return groupPayment.getId();
    }
}