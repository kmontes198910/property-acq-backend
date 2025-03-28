package com.kynsof.calendar.application.command.schedule.createall;

import com.kynsof.calendar.application.command.schedule.create.CreateScheduleCommand;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import org.springframework.stereotype.Component;

@Component
public class CreateScheduleAllCommandHandler implements ICommandHandler<CreateScheduleAllCommand> {

    private final IMediator mediator;

    public CreateScheduleAllCommandHandler(IMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void handle(CreateScheduleAllCommand command) {


        for (ScheduleAllRequest schedule : command.getSchedules()) {
            this.mediator.send(new CreateScheduleCommand(
                    command.getIdResource(),
                    command.getIdBusiness(),
                    command.getDate(),
                    schedule.getStartTime(),
                    schedule.getEndingTime(),
                    schedule.getStock(),
                    command.getServiceId(),
                    null,
                    "",
                    "",
                null,
                    false
            ));
        }
    }
}
