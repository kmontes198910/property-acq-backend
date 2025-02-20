package com.kynsof.treatments.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsof.treatments.application.command.exam.update.UpdateExamCommand;
import com.kynsof.treatments.application.command.exam.update.UpdateExamMessage;
import com.kynsof.treatments.application.command.exam.update.UpdateExamRequest;
import com.kynsof.treatments.application.command.groupPayment.create.CreateGroupPaymentCommand;
import com.kynsof.treatments.application.command.groupPayment.create.CreateGroupPaymentMessage;
import com.kynsof.treatments.application.command.groupPayment.create.CreateGroupPaymentRequest;
import com.kynsof.treatments.application.command.groupPayment.delete.DeleteGroupPaymentCommand;
import com.kynsof.treatments.application.command.groupPayment.delete.DeleteGroupPaymentMessage;
import com.kynsof.treatments.application.query.exam.search.GetSearchExamQuery;
import com.kynsof.treatments.application.query.groupPayment.getbyid.FindByIdGroupPaymentQuery;
import com.kynsof.treatments.application.query.groupPayment.getbyid.GroupPaymentResponse;
import com.kynsof.treatments.application.query.groupPayment.search.GroupPaymentQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@RequestParam UUID id) {
        DeleteGroupPaymentMessage message = mediator.send(new DeleteGroupPaymentCommand(id));
       return ResponseEntity.ok(message);
    }


    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GroupPaymentQuery query = new GroupPaymentQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateExamRequest request) {

        UpdateExamCommand command = UpdateExamCommand.fromRequest(request, id);
        UpdateExamMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/id")
    public ResponseEntity<?> getById(@RequestParam UUID id) {
        FindByIdGroupPaymentQuery query = new FindByIdGroupPaymentQuery(id);
        GroupPaymentResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

}
