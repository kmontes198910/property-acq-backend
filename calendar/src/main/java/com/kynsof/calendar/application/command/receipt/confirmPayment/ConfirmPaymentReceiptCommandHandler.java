package com.kynsof.calendar.application.command.receipt.confirmPayment;

import com.kynsof.calendar.domain.dto.ReceiptDto;
import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import com.kynsof.calendar.domain.dto.enumType.EStatusSchedule;
import com.kynsof.calendar.domain.service.IReceiptService;
import com.kynsof.calendar.application.service.http.CreateBillingPartialRequest;
import com.kynsof.calendar.application.service.http.CreateGroupPaymentUnifRequest;
import com.kynsof.calendar.application.service.http.GroupPaymentServiceClient;
import com.kynsof.share.core.application.payment.domain.placeToPlay.PaymentServiceStatusResponse;
import com.kynsof.share.core.application.payment.infrastructure.service.config.PaymentServiceClient;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConfirmPaymentReceiptCommandHandler implements ICommandHandler<ConfirmPaymentReceiptCommand> {

    private final IReceiptService service;
    private final PaymentServiceClient paymentServiceClient;
    private final GroupPaymentServiceClient groupPaymentServiceClient;

    public ConfirmPaymentReceiptCommandHandler(IReceiptService service, PaymentServiceClient paymentServiceClient, GroupPaymentServiceClient groupPaymentServiceClient) {
        this.service = service;
        this.paymentServiceClient = paymentServiceClient;
        this.groupPaymentServiceClient = groupPaymentServiceClient;
    }

    @Override
    public void handle(ConfirmPaymentReceiptCommand command) {
        PaymentServiceStatusResponse paymentStatus;

        try {
            ReceiptDto _receipt = this.service.findById(command.getReceiptId());
            // Llamar al servicio externo para obtener el estado del pago
            paymentStatus = paymentServiceClient.validateStatusPayment(command.getRequestId(), _receipt.getSchedule().getBusiness().getId());

            if (paymentStatus.getStatus().equals("APPROVED")) {
                _receipt.setReference(paymentStatus.getReference());
                _receipt.setAuthorizationCode(command.getAuthorizationCode());
                _receipt.setStatus(EStatusReceipt.PAYMENT);
                this.service.update(_receipt);

                CreateGroupPaymentUnifRequest request = getCreateGroupPaymentUnifRequest(_receipt, paymentStatus);
                try {
                    String groupPaymentId = groupPaymentServiceClient.createCompleted(request);
                    service.updateGroupPaymentId(_receipt.getId(),groupPaymentId);

                } catch (Exception e) {
                    System.err.println("Error creating group payment " + e.getMessage());
                    System.err.println(e.getMessage());
                }

            }

            if (command.getStatus().equals(EStatusReceipt.CANCEL)) {
                _receipt.getSchedule().setStock(_receipt.getSchedule().getStock() + 1);
                _receipt.getSchedule().setStatus(EStatusSchedule.AVAILABLE);
                _receipt.setStatus(command.getStatus());
            }

            if (command.getStatus().equals(EStatusReceipt.REJECTED)) {
                _receipt.getSchedule().setStock(_receipt.getSchedule().getStock() + 1);
                _receipt.getSchedule().setStatus(EStatusSchedule.AVAILABLE);
                _receipt.setStatus(command.getStatus());
            }


            if (command.getStatus().equals(EStatusReceipt.PAYMENT)) {
                if (_receipt.getSchedule().getStock() == 0) {
                    _receipt.getSchedule().setStatus(EStatusSchedule.SOLD_OUT);
                }
            }
            service.update(_receipt);

        } catch (IOException e) {
            System.err.println("Error al consultar el servicio de pagos: " + e.getMessage());
            throw new BusinessException(DomainErrorMessage.PAYMENT_NOT_FOUND, "Error al consultar el servicio de pagos.");
        } catch (Exception e) {

            System.err.println("Error durante el procesamiento del pago: " + e.getMessage());
            throw new BusinessException(DomainErrorMessage.PAYMENT_NOT_FOUND, "Error durante el procesamiento del pago.");
        }
    }

    private static CreateGroupPaymentUnifRequest getCreateGroupPaymentUnifRequest(ReceiptDto _receipt, PaymentServiceStatusResponse paymentStatus) {
        CreateGroupPaymentUnifRequest request = new CreateGroupPaymentUnifRequest();
        request.setClientId(_receipt.getUser().getId());
        request.setBusinessId(_receipt.getSchedule().getBusiness().getId());
        request.setUserSystemId(null);
        request.setUserSystemFullName(null);
        request.setPaymentType("PLACETOPAY");
        request.setPaymentStatus("PAYMENT_APPROVED");
        request.setInsuranceId(null);
        request.setTypeOperation("ExternalConsult");
        request.setAuthorizationCode(paymentStatus.getAuthorization());
        request.setReference(paymentStatus.getReference());
        request.setProforma(false);
        List<CreateBillingPartialRequest> createBillingPartialRequests = new ArrayList<>();
        CreateBillingPartialRequest createBillingPartialRequest = new CreateBillingPartialRequest();
        createBillingPartialRequest.setCode(_receipt.getService().getCode());
        createBillingPartialRequest.setDescription(_receipt.getService().getName());
        createBillingPartialRequest.setCost(_receipt.getPrice());
        createBillingPartialRequests.add(createBillingPartialRequest);
        request.setBillings(createBillingPartialRequests);
        return request;
    }
}