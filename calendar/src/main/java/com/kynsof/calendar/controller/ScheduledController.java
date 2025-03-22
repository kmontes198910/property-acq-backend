package com.kynsof.calendar.controller;

import com.kynsof.calendar.application.command.schedule.changeResource.ChangeResourceCommand;
import com.kynsof.calendar.application.command.schedule.changeResource.ChangeResourceMessage;
import com.kynsof.calendar.application.command.schedule.changeResource.ChangeResourceRequest;
import com.kynsof.calendar.application.command.schedule.create.CreateScheduleCommand;
import com.kynsof.calendar.application.command.schedule.create.CreateScheduleMessage;
import com.kynsof.calendar.application.command.schedule.create.CreateScheduleRequest;
import com.kynsof.calendar.application.command.schedule.createGoogleStyle.CreateScheduleByLoteGoogleStyleCommand;
import com.kynsof.calendar.application.command.schedule.createGoogleStyle.CreateScheduleByLoteGoogleStyleMessage;
import com.kynsof.calendar.application.command.schedule.createGoogleStyle.CreateScheduleByLoteGoogleStyleRequest;
import com.kynsof.calendar.application.command.schedule.createlote.CreateScheduleByLoteCommand;
import com.kynsof.calendar.application.command.schedule.createlote.CreateScheduleByLoteMessage;
import com.kynsof.calendar.application.command.schedule.createlote.CreateScheduleByLoteRequest;
import com.kynsof.calendar.application.command.schedule.delete.ScheduleDeleteCommand;
import com.kynsof.calendar.application.command.schedule.delete.ScheduleDeleteMessage;
import com.kynsof.calendar.application.command.schedule.update.ScheduleUpdateRequest;
import com.kynsof.calendar.application.command.schedule.update.UpdateScheduleCommand;
import com.kynsof.calendar.application.command.schedule.update.UpdateScheduleMessage;
import com.kynsof.calendar.application.query.ScheduleResponse;
import com.kynsof.calendar.application.query.schedule.getAvailabilityByRangeDate.GetAvailabilityByRangeDateQuery;
import com.kynsof.calendar.application.query.schedule.getAvailabilityByRangeDate.GetAvailabilityByRangeDateRequest;
import com.kynsof.calendar.application.query.schedule.getAvailableDatesAndSlots.AvailableDatesByResourceRequest;
import com.kynsof.calendar.application.query.schedule.getAvailableDatesAndSlots.GetAvailableDatesAndSlotsQuery;
import com.kynsof.calendar.application.query.schedule.getAvailableDatesAndSlots.GetAvailableDatesAndSlotsResponse;
import com.kynsof.calendar.application.query.schedule.getAvailableDatesByServiceId.AvailableDatesRequest;
import com.kynsof.calendar.application.query.schedule.getAvailableDatesByServiceId.GetAvailableDatesByServiceIdQuery;
import com.kynsof.calendar.application.query.schedule.getAvailableDatesByServiceId.GetAvailableDatesByServiceIdResponse;
import com.kynsof.calendar.application.query.schedule.getUniqueAvailableServices.GetUniqueAvailableServicesQuery;
import com.kynsof.calendar.application.query.schedule.getbyid.FindScheduleByIdQuery;
import com.kynsof.calendar.application.query.schedule.search.GetSearchScheduleQuery;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/scheduled")
public class ScheduledController {

    private final IMediator mediator;

    public ScheduledController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateScheduleMessage> create(@RequestBody CreateScheduleRequest createScheduleRequest,
            ServerHttpRequest request,
            @RequestHeader(value = "User-Agent", required = false,
                    defaultValue = "Unknown") String userAgent) {
        String ipAddress = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
        CreateScheduleCommand createCommand = CreateScheduleCommand.fromRequest(createScheduleRequest, userAgent, ipAddress, this.mediator);
        CreateScheduleMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-lote")
    public ResponseEntity<CreateScheduleByLoteMessage> create(@RequestBody CreateScheduleByLoteRequest request) throws Exception {

        CreateScheduleByLoteMessage response = mediator.send(new CreateScheduleByLoteCommand(
                request.getResourceId(),
                request.getBusinessId(),
                request.getServiceId(),
                request.getStartDate(),
                request.getEndDate(),
                request.getSchedules(),
                request.getDaysToExclude(),
                mediator
        ));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-lote-google-style")
    public ResponseEntity<?> createGoogleStyle(@RequestBody CreateScheduleByLoteGoogleStyleRequest request) throws Exception {

        CreateScheduleByLoteGoogleStyleMessage response = mediator.send(new CreateScheduleByLoteGoogleStyleCommand(
                request.getResource(),
                request.getBusiness(),
                request.getService(),
                request.getDays(),
                mediator
        ));

        return ResponseEntity.ok(response);
    }


    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchScheduleQuery query = new GetSearchScheduleQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/availability-by-range-date")
    public ResponseEntity<?> availability(@RequestBody GetAvailabilityByRangeDateRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getPageSize());
        GetAvailabilityByRangeDateQuery query = new GetAvailabilityByRangeDateQuery(pageable, request.getStartDate(),
                request.getEndDate(),request.getServiceId(),request.getBusinessId());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> getById(@PathVariable UUID id) {
        FindScheduleByIdQuery query = new FindScheduleByIdQuery(id);
        ScheduleResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleDeleteMessage> delete(@PathVariable("id") UUID id) {

        ScheduleDeleteCommand command = new ScheduleDeleteCommand(id);
        ScheduleDeleteMessage response = mediator.send(command);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateScheduleMessage> update(
            @PathVariable("id") UUID id,
            @RequestBody ScheduleUpdateRequest createScheduleRequest,
            ServerHttpRequest request,
            @RequestHeader(value = "User-Agent", required = false,
                    defaultValue = "Unknown") String userAgent) {
        String ipAddress = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
        UpdateScheduleCommand command = UpdateScheduleCommand.fromRequest(id, createScheduleRequest, mediator, ipAddress, userAgent);
        UpdateScheduleMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/available-dates")
    public ResponseEntity<GetAvailableDatesByServiceIdResponse> getAvailableDatesByServiceId(@RequestBody AvailableDatesRequest request) {
        GetAvailableDatesByServiceIdQuery query = new GetAvailableDatesByServiceIdQuery(request.getServiceId(),
                request.getStartDate(), request.getFinalDate());
        GetAvailableDatesByServiceIdResponse availableDates = mediator.send(query);
        return ResponseEntity.ok(availableDates);
    }

    @PostMapping("/{resourceId}/available-dates")
    public ResponseEntity<?> getAvailableDatesAndSlotsQuery(@PathVariable UUID resourceId, @RequestBody AvailableDatesByResourceRequest request) {
        GetAvailableDatesAndSlotsQuery query = new GetAvailableDatesAndSlotsQuery(resourceId, request.getBusinessId(),
                request.getStartDate(), request.getFinalDate());
        GetAvailableDatesAndSlotsResponse availableDates = mediator.send(query);
        return ResponseEntity.ok(availableDates);
    }

    @PostMapping("/services")
    public ResponseEntity<PaginatedResponse> services(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);
        GetUniqueAvailableServicesQuery query = new GetUniqueAvailableServicesQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/change-resource")
    public ResponseEntity<?> changeResource(@RequestBody ChangeResourceRequest request) {

        ChangeResourceCommand command =  ChangeResourceCommand.fromRequest( request);
        ChangeResourceMessage data = mediator.send(command);
        return ResponseEntity.ok(data);
    }

}
