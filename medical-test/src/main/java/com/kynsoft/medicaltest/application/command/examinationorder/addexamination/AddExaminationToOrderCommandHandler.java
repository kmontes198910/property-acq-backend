package com.kynsoft.medicaltest.application.command.examinationorder.addexamination;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.medicaltest.domain.entity.ExaminationOrder;
import com.kynsoft.medicaltest.domain.service.ExaminationOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddExaminationToOrderCommandHandler implements ICommandHandler<AddExaminationToOrderCommand> {
    private final ExaminationOrderService orderService;
    
    @Override
    public void handle(AddExaminationToOrderCommand command) {
        ExaminationOrder updatedOrder = orderService.addExaminationToOrder(command.getOrderId(), command.getExaminationId());
        if (updatedOrder == null) {
            throw new RuntimeException("Error al agregar el examen a la orden: " + command.getOrderId());
        }
    }
}
