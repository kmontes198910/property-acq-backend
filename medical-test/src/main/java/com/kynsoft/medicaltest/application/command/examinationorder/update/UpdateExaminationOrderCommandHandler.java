package com.kynsoft.medicaltest.application.command.examinationorder.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.medicaltest.domain.entity.ExaminationOrder;
import com.kynsoft.medicaltest.domain.exception.EntityNotFoundException;
import com.kynsoft.medicaltest.domain.service.ExaminationOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Manejador para el comando de actualización de órdenes de exámenes
 */
@Component
@RequiredArgsConstructor
public class UpdateExaminationOrderCommandHandler implements ICommandHandler<UpdateExaminationOrderCommand> {
    
    private final ExaminationOrderService orderService;
    
    public void handle(UpdateExaminationOrderCommand command) {
        // Buscar la orden existente
        ExaminationOrder existingOrder = orderService.findOrderById(command.getId())
                .orElseThrow(() -> new EntityNotFoundException("Orden de exámenes", command.getId().toString()));
        
        // Actualizar los campos que no sean nulos
        if (command.getDoctorId() != null) {
            existingOrder.setDoctorId(command.getDoctorId());
        }
        
        if (command.getStatus() != null) {
            existingOrder.setStatus(command.getStatus());
        }
        
        if (command.getObservations() != null) {
            existingOrder.setObservations(command.getObservations());
        }
        
        if (command.getBusinessId() != null) {
            existingOrder.setBusinessId(command.getBusinessId());
        }
        
        // Establecer quién actualizó la orden
        existingOrder.setUpdatedBy(command.getUpdatedBy());
        
        // Persistir los cambios
        orderService.updateOrder(existingOrder);
    }
}
