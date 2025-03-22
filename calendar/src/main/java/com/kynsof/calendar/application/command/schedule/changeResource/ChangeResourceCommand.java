package com.kynsof.calendar.application.command.schedule.changeResource;

import com.kynsof.calendar.domain.dto.enumType.EStatusSchedule;
import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class ChangeResourceCommand implements ICommand {

    private UUID scheduledId;
    private UUID resource;


    public ChangeResourceCommand(UUID scheduledId, UUID idResource) {
        this.scheduledId = scheduledId;
        this.resource = idResource;

    }

    public static ChangeResourceCommand fromRequest(ChangeResourceRequest request) {
        return new ChangeResourceCommand(request.getScheduledId(), request.getResource());
    }

    @Override
    public ICommandMessage getMessage() {
        return new ChangeResourceMessage(scheduledId);
    }
}
