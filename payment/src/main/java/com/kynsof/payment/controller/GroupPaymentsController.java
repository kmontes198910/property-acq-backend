package com.kynsof.payment.controller;

import com.kynsof.payment.application.command.groupPayment.createGroupPaymentUnif.CreateGroupPaymentUnifCommand;
import com.kynsof.payment.application.command.groupPayment.createGroupPaymentUnif.CreateGroupPaymentUnifMessage;
import com.kynsof.payment.application.command.groupPayment.createGroupPaymentUnif.CreateGroupPaymentUnifRequest;
import com.kynsof.payment.application.command.groupPayment.sendPaymentLink.SendGroupPaymentLinkCommand;
import com.kynsof.payment.application.command.groupPayment.sendPaymentLink.SendGroupPaymentLinkMessage;
import com.kynsof.payment.application.command.groupPayment.sendPaymentLink.SendGroupPaymentLinkRequest;
import com.kynsof.payment.application.command.groupPayment.updateAdminSystems.UpdateGroupPaymentAdminSystemsCommand;
import com.kynsof.payment.application.command.groupPayment.updateAdminSystems.UpdateGroupPaymentAdminSystemsMessage;
import com.kynsof.payment.application.command.groupPayment.updateAdminSystems.UpdateGroupPaymentAdminSystemsRequest;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsof.payment.application.command.groupPayment.create.CreateGroupPaymentCommand;
import com.kynsof.payment.application.command.groupPayment.create.CreateGroupPaymentMessage;
import com.kynsof.payment.application.command.groupPayment.create.CreateGroupPaymentRequest;
import com.kynsof.payment.application.command.groupPayment.delete.DeleteGroupPaymentCommand;
import com.kynsof.payment.application.command.groupPayment.delete.DeleteGroupPaymentMessage;
import com.kynsof.payment.application.command.groupPayment.update.UpdateGroupPaymentCommand;
import com.kynsof.payment.application.command.groupPayment.update.UpdateGroupPaymentMessage;
import com.kynsof.payment.application.command.groupPayment.update.UpdateGroupPaymentRequest;
import com.kynsof.payment.application.query.groupPayment.getbyid.FindByIdGroupPaymentQuery;
import com.kynsof.payment.application.query.groupPayment.getbyid.GroupPaymentResponse;
import com.kynsof.payment.application.query.groupPayment.search.GroupPaymentQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/group-payments")
public class GroupPaymentsController {

    private final IMediator mediator;

    public GroupPaymentsController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateGroupPaymentRequest requests) {

        CreateGroupPaymentCommand command = CreateGroupPaymentCommand.fromRequest(requests);
        CreateGroupPaymentMessage message = mediator.send(command);
        return ResponseEntity.ok(message);
    }

    @PostMapping("completed")
    public ResponseEntity<?> createCompleted(@RequestBody CreateGroupPaymentUnifRequest requests) {

        CreateGroupPaymentUnifCommand command = CreateGroupPaymentUnifCommand.fromRequest(requests);
        CreateGroupPaymentUnifMessage message = mediator.send(command);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@RequestParam UUID id) {
        DeleteGroupPaymentMessage message = mediator.send(new DeleteGroupPaymentCommand(id));
        return ResponseEntity.ok(message);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);
        GroupPaymentQuery query = new GroupPaymentQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateGroupPaymentRequest request) {

        UpdateGroupPaymentCommand command = UpdateGroupPaymentCommand.fromRequest(id, request);
        UpdateGroupPaymentMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/admin-systems/{id}")
    public ResponseEntity<?> updateAdminSystems(@PathVariable("id") UUID id, @RequestBody UpdateGroupPaymentAdminSystemsRequest request) {

        UpdateGroupPaymentAdminSystemsCommand command = UpdateGroupPaymentAdminSystemsCommand.fromRequest(id, request);
        UpdateGroupPaymentAdminSystemsMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        FindByIdGroupPaymentQuery query = new FindByIdGroupPaymentQuery(id);
        GroupPaymentResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestBody SendGroupPaymentLinkRequest requests) {

        SendGroupPaymentLinkCommand command = new SendGroupPaymentLinkCommand(requests.getGroupPaymentId(), requests.getType());
        SendGroupPaymentLinkMessage message = mediator.send(command);
        return ResponseEntity.ok(message);
    }
}
