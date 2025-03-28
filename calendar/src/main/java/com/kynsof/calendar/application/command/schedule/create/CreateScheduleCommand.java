package com.kynsof.calendar.application.command.schedule.create;

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
public class CreateScheduleCommand implements ICommand {

    private UUID id;
    private UUID resource;
    private UUID businessId;
    private UUID serviceId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endingTime;
    private int stock;
    private final UUID user;
    private final String ipAddress;
    private final String userAgent;
    private final IMediator mediator;
    private final boolean bloked;

    public CreateScheduleCommand(UUID idResource, UUID idBusiness, LocalDate date, LocalTime startTime, LocalTime endingTime,
                                 int stock, UUID idService, UUID user, String ipAddress, String userAgent, IMediator mediator, boolean bloked) {
        this.user = user;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.mediator = mediator;
        this.bloked = bloked;
        this.id = UUID.randomUUID();
        this.resource = idResource;
        this.businessId = idBusiness;
        this.date = date;
        this.startTime = startTime;
        this.endingTime = endingTime;
        this.stock = stock;
        this.serviceId = idService;
    }

    public static CreateScheduleCommand fromRequest(CreateScheduleRequest request, String userAgent, String ipAddress, IMediator mediator) {
        return new CreateScheduleCommand(request.getResource(), request.getBusiness(), request.getDate(),
                request.getStartTime(), request.getEndingTime(), request.getStock(),
                request.getService(), request.getUser(), ipAddress, userAgent,mediator,request.isBloked() );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateScheduleMessage(id);
    }
}
