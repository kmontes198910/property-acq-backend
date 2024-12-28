package com.kynsof.calendar.application.command.receipt.cancel;

import com.kynsof.calendar.domain.dto.ReceiptDto;
import com.kynsof.calendar.domain.dto.ScheduleDto;
import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import com.kynsof.calendar.domain.dto.enumType.EStatusSchedule;
import com.kynsof.calendar.domain.service.IReceiptService;
import com.kynsof.calendar.domain.service.IScheduleService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class CancelReceiptCommandHandler implements ICommandHandler<CancelReceiptCommand> {

    private final IReceiptService service;
    private final IScheduleService scheduleService;

    public CancelReceiptCommandHandler(IReceiptService service, IScheduleService scheduleService) {
        this.service = service;
        this.scheduleService = scheduleService;
    }

    @Override
    public void handle(CancelReceiptCommand command) {
        ReceiptDto _receipt = this.service.findById(command.getReceiptId());
        _receipt.setStatus(EStatusReceipt.CANCEL);
        ScheduleDto _sSchedule = this.scheduleService.findById(_receipt.getSchedule().getId());
        _sSchedule.setStock(_sSchedule.getStock() + 1);
        _sSchedule.setStatus(EStatusSchedule.AVAILABLE);
        scheduleService.update(_sSchedule);
        service.update(_receipt);
    }
}
