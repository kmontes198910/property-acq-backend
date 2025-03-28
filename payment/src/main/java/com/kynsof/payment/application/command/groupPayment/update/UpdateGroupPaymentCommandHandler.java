package com.kynsof.payment.application.command.groupPayment.update;

import com.kynsof.payment.domain.dto.GroupPaymentDto;
import com.kynsof.payment.domain.dto.enumDto.GroupPaymentStatus;
import com.kynsof.payment.domain.service.IGroupPaymentService;
import com.kynsof.share.core.application.payment.domain.placeToPlay.PaymentServiceStatusResponse;
import com.kynsof.share.core.application.payment.infrastructure.service.config.PaymentServiceClient;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UpdateGroupPaymentCommandHandler implements ICommandHandler<UpdateGroupPaymentCommand> {

    private final IGroupPaymentService serviceImpl;
    ;
    private final PaymentServiceClient paymentServiceClient;

    public UpdateGroupPaymentCommandHandler(IGroupPaymentService serviceImpl, PaymentServiceClient paymentServiceClient) {
        this.serviceImpl = serviceImpl;

        this.paymentServiceClient = paymentServiceClient;
    }

    @Override
    public void handle(UpdateGroupPaymentCommand command) {
        GroupPaymentDto groupPaymentDto = serviceImpl.findById(command.getId());
        try {
            if (command.getStatus().equals(GroupPaymentStatus.PENDING_APPROVED) || command.getStatus().equals(GroupPaymentStatus.PAYMENT_APPROVED)) {
                System.err.println("Entro aqui");
                PaymentServiceStatusResponse paymentStatus = paymentServiceClient.validateStatusPayment(
                        command.getRequestId(), groupPaymentDto.getBusiness().getId());
                GroupPaymentStatus groupPaymentStatus = GroupPaymentStatus.PENDING_APPROVED;
                if (paymentStatus.getStatus().equals("APPROVED")) {
                    groupPaymentStatus = GroupPaymentStatus.PAYMENT_APPROVED;
                    this.serviceImpl.update(groupPaymentDto.getId(), paymentStatus.getReference(), paymentStatus.getAuthorization(), command.getRequestId(),
                            command.getProcessUrl(), groupPaymentStatus
                    );
                } else if (paymentStatus.getStatus().equals("REJECTED")) {
                    groupPaymentStatus = GroupPaymentStatus.REJECTED;
                    this.serviceImpl.update(groupPaymentDto.getId(), paymentStatus.getReference(), paymentStatus.getAuthorization(), command.getRequestId(),
                            command.getProcessUrl(), groupPaymentStatus
                    );

                }
                else {
                    this.serviceImpl.update(command.getId(), command.getReference(),
                            command.getAuthorizationCode(), command.getRequestId(),
                            command.getProcessUrl(),
                            command.getStatus());
                }
            } else {
                this.serviceImpl.update(command.getId(), command.getReference(),
                        command.getAuthorizationCode(), command.getRequestId(),
                        command.getProcessUrl(),
                        command.getStatus());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        command.setResult(true);

    }
}
