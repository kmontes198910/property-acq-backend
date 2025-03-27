package com.kynsof.calendar.application.command.receipt.updateConsultId;

import com.kynsof.calendar.domain.dto.ReceiptDto;
import com.kynsof.calendar.domain.service.IReceiptService;
import com.kynsof.calendar.domain.service.IScheduleService;
import com.kynsof.calendar.application.service.BusinessBalanceService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class UpdateConsultIdCommandHandler implements ICommandHandler<UpdateConsultIdCommand> {

    private final IReceiptService receiptService;
    private final IScheduleService serviceSchedule;
    private final BusinessBalanceService businessBalanceService;

    public UpdateConsultIdCommandHandler(IReceiptService service, IScheduleService scheduleService, BusinessBalanceService businessBalanceService) {
        this.receiptService = service;
        this.serviceSchedule = scheduleService;
        this.businessBalanceService = businessBalanceService;
    }

    @Override
    public void handle(UpdateConsultIdCommand command) {
        ReceiptDto receiptDto = receiptService.findById(command.getReceiptId());
        receiptDto.setConsultId(command.getConsultId().toString());
        receiptService.update(receiptDto);
       command.setResult(true);
    }

}
