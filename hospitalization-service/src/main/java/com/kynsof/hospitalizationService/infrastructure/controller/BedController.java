package com.kynsof.hospitalizationService.infrastructure.controller;

import com.kynsof.hospitalizationService.application.command.bed.create.CreateBedCommand;
import com.kynsof.hospitalizationService.application.command.bed.create.CreateBedMessage;
import com.kynsof.hospitalizationService.application.command.bed.create.CreateBedRequest;
import com.kynsof.hospitalizationService.application.command.bed.delete.DeleteBedCommand;
import com.kynsof.hospitalizationService.application.command.bed.delete.DeleteBedMessage;
import com.kynsof.hospitalizationService.application.command.bed.update.UpdateBedCommand;
import com.kynsof.hospitalizationService.application.command.bed.update.UpdateBedMessage;
import com.kynsof.hospitalizationService.application.command.bed.update.UpdateBedRequest;
import com.kynsof.hospitalizationService.application.query.bed.getById.GetByIdBedQuery;
import com.kynsof.hospitalizationService.application.query.bed.search.GetSearchBedQuery;
import com.kynsof.hospitalizationService.application.response.BedResponse;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bed")
public class BedController {

    private final IMediator mediator;

    public BedController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateBedMessage> createAllergy(@RequestBody CreateBedRequest request) {
        CreateBedCommand createCommand = CreateBedCommand.fromRequest(request);
        CreateBedMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateBedRequest request) {

        UpdateBedCommand command = UpdateBedCommand.fromRequest(request, id);
        UpdateBedMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteBedCommand command = new DeleteBedCommand(id);
        DeleteBedMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdBedQuery query = new GetByIdBedQuery(id);
        BedResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchBedQuery query = new GetSearchBedQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
