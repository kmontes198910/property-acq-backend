package com.kynsof.calendar.application.command.receipt.updateStatus;

import com.kynsof.calendar.domain.dto.ReceiptDto;
import com.kynsof.calendar.domain.dto.ScheduleDto;
import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import com.kynsof.calendar.domain.dto.enumType.EStatusSchedule;
import com.kynsof.calendar.domain.service.IReceiptService;
import com.kynsof.calendar.domain.service.IScheduleService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class UpdateStatusReceiptCommandHandler implements ICommandHandler<UpdateStatusReceiptCommand> {

    private final IReceiptService receiptService;
    private final IScheduleService serviceSchedule;


    public UpdateStatusReceiptCommandHandler(IReceiptService service, IScheduleService scheduleService) {
        this.receiptService = service;
        this.serviceSchedule = scheduleService;
    }

    @Override
    public void handle(UpdateStatusReceiptCommand command) {
        ReceiptDto receiptDto = this.receiptService.findById(command.getId());

        ScheduleDto _schedule = serviceSchedule.findById(receiptDto.getSchedule().getId());
        //_schedule.setStatus(EStatusSchedule.ATTENDED);

        if (command.getStatus().equals(EStatusReceipt.CANCEL)) {
            receiptService.updateStatus(receiptDto.getId(),EStatusReceipt.CANCEL);
            cleanStock(_schedule);

        }
        if (command.getStatus().equals(EStatusReceipt.REJECTED)) {
            cleanStock(_schedule);
            receiptService.updateStatus(receiptDto.getId(),EStatusReceipt.REJECTED);
        } else {
            receiptService.updateStatus(receiptDto.getId(), command.getStatus());
        }


      //  serviceSchedule.update(_schedule);
    }

    private void cleanStock(ScheduleDto scheduleDto) {
        scheduleDto.setStock(scheduleDto.getStock() + 1);
        scheduleDto.setStatus(EStatusSchedule.AVAILABLE);
        serviceSchedule.update(scheduleDto);
    }
}
