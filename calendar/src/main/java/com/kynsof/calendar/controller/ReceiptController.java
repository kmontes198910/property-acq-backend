package com.kynsof.calendar.controller;

import com.kynsof.calendar.application.command.receipt.confirmPayment.ConfirmPaymentReceiptCommand;
import com.kynsof.calendar.application.command.receipt.confirmPayment.ConfirmPaymentReceiptMessage;
import com.kynsof.calendar.application.command.receipt.confirmPayment.ConfirmPaymentReceiptRequest;
import com.kynsof.calendar.application.command.receipt.create.CreateReceiptCommand;
import com.kynsof.calendar.application.command.receipt.create.CreateReceiptMessage;
import com.kynsof.calendar.application.command.receipt.create.CreateReceiptRequest;
import com.kynsof.calendar.application.command.receipt.createReceiptScheduled.CreateScheduleReceiptCommand;
import com.kynsof.calendar.application.command.receipt.createReceiptScheduled.CreateScheduleReceiptRequest;
import com.kynsof.calendar.application.command.receipt.delete.ReceiptDeleteCommand;
import com.kynsof.calendar.application.command.receipt.delete.ReceiptDeleteMessage;
import com.kynsof.calendar.application.command.receipt.reschedule.RescheduleReceiptCommand;
import com.kynsof.calendar.application.command.receipt.reschedule.RescheduleReceiptMessage;
import com.kynsof.calendar.application.command.receipt.reschedule.RescheduleReceiptRequest;
import com.kynsof.calendar.application.command.receipt.setRequest.SetRequestIdCommand;
import com.kynsof.calendar.application.command.receipt.setRequest.SetRequestIdMessage;
import com.kynsof.calendar.application.command.receipt.setRequest.SetRequestIdRequest;
import com.kynsof.calendar.application.command.receipt.updateConsultId.UpdateConsultIdCommand;
import com.kynsof.calendar.application.command.receipt.updateConsultId.UpdateConsultIdMessage;
import com.kynsof.calendar.application.command.receipt.updateConsultId.UpdateConsultIdRequest;
import com.kynsof.calendar.application.command.receipt.updateStatus.UpdateStatusReceiptCommand;
import com.kynsof.calendar.application.command.receipt.updateStatus.UpdateStatusReceiptMessage;
import com.kynsof.calendar.application.command.receipt.updateStatus.UpdateStatusReceiptRequest;
import com.kynsof.calendar.application.query.ReceiptResponse;
import com.kynsof.calendar.application.query.receipt.getbyid.FindReceiptByIdQuery;
import com.kynsof.calendar.application.query.receipt.search.GetSearchReceiptQuery;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/receipt")
public class ReceiptController {

    private final IMediator mediator;

    public ReceiptController(IMediator mediator){

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateReceiptRequest createReceiptRequest,
                                                       ServerHttpRequest request,
                                                       @RequestHeader(value = "User-Agent", required = false,
                                                               defaultValue = "Unknown") String userAgent) {

        String ipAddress = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();

        CreateReceiptCommand createCommand = CreateReceiptCommand.fromRequest(createReceiptRequest, ipAddress, userAgent);
        CreateReceiptMessage response = mediator.send(createCommand);
        return ResponseEntity.ok(response);
    }

    @PostMapping("with-scheduled")
    public ResponseEntity<?> createWithScheduled(@RequestBody CreateScheduleReceiptRequest createScheduleReceiptRequest,
                                                       ServerHttpRequest request,
                                                       @RequestHeader(value = "User-Agent", required = false,
                                                               defaultValue = "Unknown") String userAgent) {

        String ipAddress = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();

        CreateScheduleReceiptCommand createCommand = CreateScheduleReceiptCommand.fromRequest(createScheduleReceiptRequest,
                ipAddress, userAgent);
        CreateReceiptMessage response = mediator.send(createCommand);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/reschedule")
    public ResponseEntity<?> reschedule(@RequestBody RescheduleReceiptRequest rescheduleReceiptRequest,
                                    ServerHttpRequest request,
                                    @RequestHeader(value = "User-Agent", required = false,
                                            defaultValue = "Unknown") String userAgent) {

        String ipAddress = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();

        RescheduleReceiptCommand createCommand = RescheduleReceiptCommand.fromRequest(rescheduleReceiptRequest, ipAddress, userAgent);
        RescheduleReceiptMessage response = mediator.send(createCommand);
        return ResponseEntity.ok(response);
    }

    @PostMapping("receipt-confirm-payment/{receiptId}")
    public ResponseEntity<ConfirmPaymentReceiptMessage> confirmPayment(@PathVariable UUID receiptId,
                                                                       @RequestBody ConfirmPaymentReceiptRequest confirmPaymentReceiptRequest,
                                                                       ServerHttpRequest request,
                                                                       @RequestHeader(value = "User-Agent", required = false,
                                                                               defaultValue = "Unknown") String userAgent) {

        String ipAddress = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
        ConfirmPaymentReceiptCommand createCommand = ConfirmPaymentReceiptCommand.fromRequest(receiptId,
                confirmPaymentReceiptRequest, ipAddress, userAgent );
        ConfirmPaymentReceiptMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ReceiptResponse> getById(@PathVariable UUID id) {

        FindReceiptByIdQuery query = new FindReceiptByIdQuery(id);
        ReceiptResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchReceiptQuery query = new GetSearchReceiptQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReceiptDeleteMessage> delete(@PathVariable("id") UUID id) {

        ReceiptDeleteCommand command = new ReceiptDeleteCommand(id);
        ReceiptDeleteMessage response = mediator.send(command);

        return ResponseEntity.ok(response);
    }



    @PostMapping("/change-status/{receiptId}")
    public ResponseEntity<?> changeStatus(@PathVariable UUID receiptId, @RequestBody UpdateStatusReceiptRequest confirmPaymentReceiptRequest) {

        UpdateStatusReceiptCommand createCommand = UpdateStatusReceiptCommand.fromRequest(receiptId,
                confirmPaymentReceiptRequest );
        UpdateStatusReceiptMessage response = mediator.send(createCommand);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-consultId")
    public ResponseEntity<?> changeConsultId( @RequestBody UpdateConsultIdRequest confirmPaymentReceiptRequest) {

        UpdateConsultIdCommand createCommand = UpdateConsultIdCommand.fromRequest(
                confirmPaymentReceiptRequest );
        UpdateConsultIdMessage response = mediator.send(createCommand);
        return ResponseEntity.ok(response);
    }

    @PostMapping("set-request/{receiptId}")
    public ResponseEntity<?> setRequest(@PathVariable UUID receiptId,
                                                                       @RequestBody SetRequestIdRequest confirmPaymentReceiptRequest,
                                                                       ServerHttpRequest request,
                                                                       @RequestHeader(value = "User-Agent", required = false,
                                                                               defaultValue = "Unknown") String userAgent) {

        String ipAddress = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
        SetRequestIdCommand createCommand = SetRequestIdCommand.fromRequest(receiptId,
                confirmPaymentReceiptRequest);
        SetRequestIdMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

}
