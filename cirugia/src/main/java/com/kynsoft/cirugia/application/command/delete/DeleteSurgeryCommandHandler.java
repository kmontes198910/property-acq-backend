package com.kynsoft.cirugia.application.command.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.infrastructure.repository.command.SurgeryWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.SurgeryReadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteSurgeryCommandHandler implements ICommandHandler<DeleteSurgeryCommand> {

    private final SurgeryReadRepository surgeryReadRepository;
    private final SurgeryWriteRepository surgeryWriteRepository;

    @Override
    @Transactional
    public void handle(DeleteSurgeryCommand command) {
        log.info("Deleting surgery with ID: {}", command.getSurgeryId());
        
        if (!surgeryReadRepository.existsById(command.getSurgeryId())) {
            log.warn("Surgery not found with ID: {}", command.getSurgeryId());
            // Aunque la operación no haya tenido éxito, devolvemos el mensaje del comando
            // El código de llamada debería verificar el resultado por otros medios
        } else {
            surgeryWriteRepository.deleteById(command.getSurgeryId());
        }


    }
}