package com.kynsof.calendar.application.command.receipt.changeResource;

import com.kynsof.calendar.domain.dto.ReceiptDto;
import com.kynsof.calendar.domain.dto.ScheduleDto;
import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import com.kynsof.calendar.domain.dto.enumType.EStatusSchedule;
import com.kynsof.calendar.domain.service.IReceiptService;
import com.kynsof.calendar.domain.service.IScheduleService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class ChangeResourceReceiptCommandHandler implements ICommandHandler<ChangeResourceReceiptCommand> {

    private final IReceiptService receiptService;



    public ChangeResourceReceiptCommandHandler(IReceiptService service) {
        this.receiptService = service;

    }

    @Override
    public void handle(ChangeResourceReceiptCommand command) {

        receiptService.updateResource(command.getId(), command.getResourceId());

    }


}
