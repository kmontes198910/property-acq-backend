package com.kynsof.calendar.application.command.receipt.reverse;

import com.kynsof.calendar.domain.dto.ReceiptDto;
import com.kynsof.calendar.domain.dto.ScheduleDto;
import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import com.kynsof.calendar.domain.service.IReceiptService;
import com.kynsof.calendar.domain.service.IScheduleService;
import com.kynsof.calendar.infrastructure.entity.Receipt;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReverseReceiptCommandHandler implements ICommandHandler<ReverseReceiptCommand> {

    private final IReceiptService receiptService;
    private final IScheduleService serviceSchedule;

    public ReverseReceiptCommandHandler(IReceiptService service, IScheduleService scheduleService
    ) {
        this.receiptService = service;
        this.serviceSchedule = scheduleService;
    }

    @Override
    public void handle(ReverseReceiptCommand command) {
        Optional<Receipt> entity = this.receiptService.findByRequestId(command.getRequestId());

        if (entity.isPresent()) {
            Receipt receipt = entity.get();
            ReceiptDto dto = receipt.toAggregate();
            ScheduleDto _schedule = serviceSchedule.findById(receipt.getSchedule().getId());
            dto.setStatus(EStatusReceipt.CANCEL);
            cleanStock(_schedule);
            receiptService.update(dto);
        }

    }

    private void cleanStock(ScheduleDto scheduleDto) {
        scheduleDto.setStock(scheduleDto.getStock() + 1);
        serviceSchedule.update(scheduleDto);
    }
}
