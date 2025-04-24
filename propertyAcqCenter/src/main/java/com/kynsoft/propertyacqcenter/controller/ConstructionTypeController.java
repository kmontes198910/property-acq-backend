package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.constructionType.create.CreateConstructionTypeCommand;
import com.kynsoft.propertyacqcenter.application.command.constructionType.create.CreateConstructionTypeMessage;
import com.kynsoft.propertyacqcenter.application.command.constructionType.create.CreateConstructionTypeRequest;
import com.kynsoft.propertyacqcenter.application.command.constructionType.delete.DeleteConstructionTypeCommand;
import com.kynsoft.propertyacqcenter.application.command.constructionType.delete.DeleteConstructionTypeMessage;
import com.kynsoft.propertyacqcenter.application.command.constructionType.update.UpdateConstructionTypeCommand;
import com.kynsoft.propertyacqcenter.application.command.constructionType.update.UpdateConstructionTypeMessage;
import com.kynsoft.propertyacqcenter.application.command.constructionType.update.UpdateConstructionTypeRequest;
import com.kynsoft.propertyacqcenter.application.query.constructionType.getById.GetByIdConstructionTypeQuery;
import com.kynsoft.propertyacqcenter.application.query.constructionType.search.GetSearchConstructionTypeQuery;
import com.kynsoft.propertyacqcenter.application.response.ConstructionTypeResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/construction-type")
public class ConstructionTypeController {

    private final IMediator mediator;

    public ConstructionTypeController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateConstructionTypeMessage> createAllergy(@RequestBody CreateConstructionTypeRequest request) {
        CreateConstructionTypeCommand createCommand = CreateConstructionTypeCommand.fromRequest(request);
        CreateConstructionTypeMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateConstructionTypeRequest request) {

        UpdateConstructionTypeCommand command = UpdateConstructionTypeCommand.fromRequest(request, id);
        UpdateConstructionTypeMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteConstructionTypeCommand command = new DeleteConstructionTypeCommand(id);
        DeleteConstructionTypeMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdConstructionTypeQuery query = new GetByIdConstructionTypeQuery(id);
        ConstructionTypeResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchConstructionTypeQuery query = new GetSearchConstructionTypeQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
