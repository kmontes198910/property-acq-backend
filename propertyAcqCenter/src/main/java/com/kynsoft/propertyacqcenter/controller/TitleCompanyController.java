package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.titleCompany.create.CreateTitleCompanyCommand;
import com.kynsoft.propertyacqcenter.application.command.titleCompany.create.CreateTitleCompanyMessage;
import com.kynsoft.propertyacqcenter.application.command.titleCompany.create.CreateTitleCompanyRequest;
import com.kynsoft.propertyacqcenter.application.command.titleCompany.delete.DeleteTitleCompanyCommand;
import com.kynsoft.propertyacqcenter.application.command.titleCompany.delete.DeleteTitleCompanyMessage;
import com.kynsoft.propertyacqcenter.application.command.titleCompany.update.UpdateTitleCompanyCommand;
import com.kynsoft.propertyacqcenter.application.command.titleCompany.update.UpdateTitleCompanyMessage;
import com.kynsoft.propertyacqcenter.application.command.titleCompany.update.UpdateTitleCompanyRequest;
import com.kynsoft.propertyacqcenter.application.query.titleCompany.getById.GetByIdTitleCompanyQuery;
import com.kynsoft.propertyacqcenter.application.query.titleCompany.search.GetSearchTitleCompanyQuery;
import com.kynsoft.propertyacqcenter.application.response.TitleCompanyResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/title-company")
public class TitleCompanyController {

    private final IMediator mediator;

    public TitleCompanyController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateTitleCompanyRequest request) {
        CreateTitleCompanyCommand createCommand = CreateTitleCompanyCommand.fromRequest(request);
        CreateTitleCompanyMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateTitleCompanyRequest request) {

        UpdateTitleCompanyCommand command = UpdateTitleCompanyCommand.fromRequest(request, id);
        UpdateTitleCompanyMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteTitleCompanyCommand command = new DeleteTitleCompanyCommand(id);
        DeleteTitleCompanyMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdTitleCompanyQuery query = new GetByIdTitleCompanyQuery(id);
        TitleCompanyResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchTitleCompanyQuery query = new GetSearchTitleCompanyQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }
}
