package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateAdquisitionPropertyCommand;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateAdquisitionPropertyMessage;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateAdquisitionPropertyRequest;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.delete.DeleteAdquisitionPropertyCommand;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.delete.DeleteAdquisitionPropertyMessage;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.UpdateAdquisitionPropertyCommand;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.UpdateAdquisitionPropertyMessage;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.UpdateAdquisitionPropertyRequest;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.bankStatementRequest.UpdateBankStatementRequestCommand;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.bankStatementRequest.UpdateBankStatementRequestMessage;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.bankStatementRequest.UpdateBankStatementRequestRequest;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.buyerPersonalBankStatementRequest.UpdateBuyerPersonalBankStatementRequestCommand;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.buyerPersonalBankStatementRequest.UpdateBuyerPersonalBankStatementRequestMessage;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.buyerPersonalBankStatementRequest.UpdateBuyerPersonalBankStatementRequestRequest;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.contractAddendum.UpdateContractAddendumCommand;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.contractAddendum.UpdateContractAddendumMessage;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.contractAddendum.UpdateContractAddendumRequest;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.sellerBankStatementRequest.UpdateSellerBankStatementCommand;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.sellerBankStatementRequest.UpdateSellerBankStatementMessage;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.sellerBankStatementRequest.UpdateSellerBankStatementRequest;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.sellerPersonalBankStatements.UpdateSellerPersonalBankStatementsRequestCommand;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.sellerPersonalBankStatements.UpdateSellerPersonalBankStatementsRequestMessage;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.sellerPersonalBankStatements.UpdateSellerPersonalBankStatementsRequestRequest;
import com.kynsoft.propertyacqcenter.application.query.adquisitionProperty.getById.GetByIdAdquisitionPropertyQuery;
import com.kynsoft.propertyacqcenter.application.query.adquisitionProperty.getByPropertyId.FindByAdquisitionPropertyByPropertyIdQuery;
import com.kynsoft.propertyacqcenter.application.query.adquisitionProperty.search.GetSearchAdquisitionPropertyQuery;
import com.kynsoft.propertyacqcenter.application.response.AdquisitionPropertyListResponse;
import com.kynsoft.propertyacqcenter.application.response.AdquisitionPropertyResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buyer-acquisition-property")
public class BuyerAdquisitionPropertyController {

    private final IMediator mediator;

    public BuyerAdquisitionPropertyController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateAdquisitionPropertyMessage> create(@RequestBody CreateAdquisitionPropertyRequest request) {
        CreateAdquisitionPropertyCommand createCommand = CreateAdquisitionPropertyCommand.fromRequest(request);
        CreateAdquisitionPropertyMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateAdquisitionPropertyRequest request) {

        UpdateAdquisitionPropertyCommand command = UpdateAdquisitionPropertyCommand.fromRequest(request, id);
        UpdateAdquisitionPropertyMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/bank-statement-request/{id}")
    public ResponseEntity<?> UpdateBankStatementRequest(@PathVariable("id") UUID id, @RequestBody UpdateBankStatementRequestRequest request) {

        UpdateBankStatementRequestCommand command = UpdateBankStatementRequestCommand.fromRequest(request, id);
        UpdateBankStatementRequestMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/buyer_personal_bank_statements/{id}")
    public ResponseEntity<?> UpdateBuyerPersonalBankStatements(@PathVariable("id") UUID id, @RequestBody UpdateBuyerPersonalBankStatementRequestRequest request) {

        UpdateBuyerPersonalBankStatementRequestCommand command = UpdateBuyerPersonalBankStatementRequestCommand.fromRequest(request, id);
        UpdateBuyerPersonalBankStatementRequestMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/seller_personal_bank_statements/{id}")
    public ResponseEntity<?> UpdateSellerPersonalBankStatements(@PathVariable("id") UUID id, @RequestBody UpdateSellerPersonalBankStatementsRequestRequest request) {

        UpdateSellerPersonalBankStatementsRequestCommand command = UpdateSellerPersonalBankStatementsRequestCommand.fromRequest(request, id);
        UpdateSellerPersonalBankStatementsRequestMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/seller_bank_statement_request/{id}")
    public ResponseEntity<?> UpdateSellerPersonalBankStatements(@PathVariable("id") UUID id, @RequestBody UpdateSellerBankStatementRequest request) {

        UpdateSellerBankStatementCommand command = UpdateSellerBankStatementCommand.fromRequest(request, id);
        UpdateSellerBankStatementMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/contract_addendum/{id}")
    public ResponseEntity<?> UpdateSellerPersonalBankStatements(@PathVariable("id") UUID id, @RequestBody UpdateContractAddendumRequest request) {

        UpdateContractAddendumCommand command = UpdateContractAddendumCommand.fromRequest(request, id);
        UpdateContractAddendumMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteAdquisitionPropertyCommand command = new DeleteAdquisitionPropertyCommand(id);
        DeleteAdquisitionPropertyMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdAdquisitionPropertyQuery query = new GetByIdAdquisitionPropertyQuery(id);
        AdquisitionPropertyResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/property/{id}")
    public ResponseEntity<?> getByPropertyId(@PathVariable String id) {

        FindByAdquisitionPropertyByPropertyIdQuery query = new FindByAdquisitionPropertyByPropertyIdQuery(id);
        AdquisitionPropertyListResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchAdquisitionPropertyQuery query = new GetSearchAdquisitionPropertyQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
