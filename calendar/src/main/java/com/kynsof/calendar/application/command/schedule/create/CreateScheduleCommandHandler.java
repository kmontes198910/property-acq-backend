package com.kynsof.calendar.application.command.schedule.create;

import com.kynsof.calendar.application.command.receipt.create.CreateReceiptCommand;
import com.kynsof.calendar.domain.dto.BusinessDto;
import com.kynsof.calendar.domain.dto.ResourceDto;
import com.kynsof.calendar.domain.dto.ScheduleDto;
import com.kynsof.calendar.domain.dto.ServiceDto;
import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import com.kynsof.calendar.domain.dto.enumType.EStatusSchedule;
import com.kynsof.calendar.domain.service.IBusinessService;
import com.kynsof.calendar.domain.service.IResourceService;
import com.kynsof.calendar.domain.service.IScheduleService;
import com.kynsof.calendar.domain.service.IServiceService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateScheduleCommandHandler implements ICommandHandler<CreateScheduleCommand> {

    private final IScheduleService service;
    private final IResourceService serviceResource;
    private final IBusinessService serviceBusiness;
    private final IServiceService serviceService;
    private IMediator mediator;

    public CreateScheduleCommandHandler(IScheduleService service, IResourceService serviceResource,
            IBusinessService serviceBusiness,
            IServiceService serviceService) {
        this.service = service;
        this.serviceResource = serviceResource;
        this.serviceBusiness = serviceBusiness;
        this.serviceService = serviceService;
    }

    @Override
    public void handle(CreateScheduleCommand command) {
        this.mediator = command.getMediator();
        ResourceDto _resource = this.serviceResource.findById(command.getResource());
        BusinessDto _business = this.serviceBusiness.findById(command.getBusinessId());
        ServiceDto _service = this.serviceService.findByIds(command.getServiceId());
        if (this.service.existsOverlappingSchedule(command.getResource(), command.getDate(), command.getStartTime(), command.getEndingTime())) {
            throw new BusinessNotFoundException(
                    new GlobalBusinessException(DomainErrorMessage.SCHEDULED_TASK_ALREADY_EXISTS,
                            new ErrorField("id", "A scheduled task for this service already exists.")));
        }
        UUID id = UUID.randomUUID();
        int initialStock = command.getStock() == 0 ? 1 : command.getStock();
        UUID result = service.create(new ScheduleDto(id, _resource, _business, command.getDate(), command.getStartTime(), command.getEndingTime(),
                initialStock, initialStock, EStatusSchedule.AVAILABLE, _service));
        command.setId(result);

        if (command.getUser() != null) {
            CreateReceiptCommand createScheduleReceiptCommand = new CreateReceiptCommand(
                    _service.getNormalAppointmentPrice(),
                    false,
                    "",
                    command.getUser(),
                    result,
                    _service.getId(),
                    command.getIpAddress(),
                    command.getUserAgent(),
                    EStatusReceipt.PENDING
            );
            mediator.send(createScheduleReceiptCommand);
        }
    }
}
