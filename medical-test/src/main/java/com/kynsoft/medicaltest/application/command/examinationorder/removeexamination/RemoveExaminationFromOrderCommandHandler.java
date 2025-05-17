package com.kynsoft.medicaltest.application.command.examinationorder.removeexamination;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.medicaltest.domain.entity.ExaminationOrder;
import com.kynsoft.medicaltest.domain.service.ExaminationOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoveExaminationFromOrderCommandHandler implements ICommandHandler<RemoveExaminationFromOrderCommand> {
    private final ExaminationOrderService orderService;
    
    @Override
    public void handle(RemoveExaminationFromOrderCommand command) {
        ExaminationOrder updatedOrder = orderService.removeExaminationFromOrder(command.getOrderId(), command.getExaminationId());
        if (updatedOrder == null) {
            throw new RuntimeException("Error al eliminar el examen de la orden: " + command.getOrderId());
        }
    }
}
