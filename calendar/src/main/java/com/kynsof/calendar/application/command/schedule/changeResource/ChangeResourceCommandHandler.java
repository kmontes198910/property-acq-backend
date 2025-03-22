package com.kynsof.calendar.application.command.schedule.changeResource;

import com.kynsof.calendar.domain.dto.ResourceDto;
import com.kynsof.calendar.domain.dto.ScheduleDto;
import com.kynsof.calendar.domain.service.IResourceService;
import com.kynsof.calendar.domain.service.IScheduleService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class ChangeResourceCommandHandler implements ICommandHandler<ChangeResourceCommand> {

    private final IScheduleService scheduleService;
    private final IResourceService resourceService;

    public ChangeResourceCommandHandler(IScheduleService scheduleService, IResourceService resourceService) {
        this.scheduleService = scheduleService;
        this.resourceService = resourceService;
    }

    @Override
    public void handle(ChangeResourceCommand command) {
        ScheduleDto scheduleDto = scheduleService.findById(command.getScheduledId());
        ResourceDto resourceDto = resourceService.findById(command.getResource());
        scheduleDto.setResource(resourceDto);
        scheduleService.update(scheduleDto);
    }
}
