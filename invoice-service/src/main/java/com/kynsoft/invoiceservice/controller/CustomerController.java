package com.kynsoft.invoiceservice.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.invoiceservice.application.command.customer.create.CreateCustomerCommand;
import com.kynsoft.invoiceservice.application.command.customer.create.CreateCustomerMessage;
import com.kynsoft.invoiceservice.application.command.customer.create.CreateCustomerRequest;
import com.kynsoft.invoiceservice.application.command.customer.delete.DeleteCustomerCommand;
import com.kynsoft.invoiceservice.application.command.customer.update.UpdateCustomerCommand;
import com.kynsoft.invoiceservice.application.command.customer.update.UpdateCustomerMessage;
import com.kynsoft.invoiceservice.application.command.customer.update.UpdateCustomerRequest;
import com.kynsoft.invoiceservice.application.query.customer.get.CustomerResponse;
import com.kynsoft.invoiceservice.application.query.customer.get.GetCustomerByIdQuery;
import com.kynsoft.invoiceservice.application.query.customer.search.SearchCustomerAdvancedQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Clientes", description = "API para la gestión de clientes")
public class CustomerController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_NAME_HEADER = "X-User-Name";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un nuevo cliente", 
               description = "Registra un nuevo cliente en el sistema")
    @ApiResponse(responseCode = "201", 
                 description = "Cliente creado correctamente",
                 content = @Content(mediaType = "application/json", 
                                   schema = @Schema(implementation = CreateCustomerMessage.class)))
    public ResponseEntity<CreateCustomerMessage> createCustomer(
            @Parameter(description = "Datos del cliente a crear", required = true) 
            @RequestBody CreateCustomerRequest request,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId) {
        
        log.info("Creando nuevo cliente: {}", request.getBusinessName());
        
        UUID userUuid = userId != null ? UUID.fromString(userId) : null;
        CreateCustomerCommand command = CreateCustomerCommand.fromRequest(request, userUuid);
        CreateCustomerMessage response = mediator.send(command);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID", 
               description = "Recupera un cliente específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Cliente encontrado",
                     content = @Content(mediaType = "application/json", 
                                       schema = @Schema(implementation = CustomerResponse.class))),
        @ApiResponse(responseCode = "404", 
                     description = "Cliente no encontrado",
                     content = @Content)
    })
    public ResponseEntity<CustomerResponse> getCustomerById(
            @Parameter(description = "ID del cliente a buscar", required = true) 
            @PathVariable UUID id) {
        
        log.info("Buscando cliente con ID: {}", id);
        
        GetCustomerByIdQuery query = new GetCustomerByIdQuery(id);
        CustomerResponse response = mediator.send(query);
        
        return ResponseEntity.ok(response);
    }

    
    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar un cliente", 
               description = "Actualiza los datos de un cliente existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Cliente actualizado correctamente",
                     content = @Content(mediaType = "application/json", 
                                       schema = @Schema(implementation = UpdateCustomerMessage.class))),
        @ApiResponse(responseCode = "404", 
                     description = "Cliente no encontrado",
                     content = @Content)
    })
    public ResponseEntity<UpdateCustomerMessage> updateCustomer(
            @Parameter(description = "ID del cliente a actualizar", required = true) 
            @PathVariable UUID id, 
            @Parameter(description = "Datos actualizados del cliente", required = true) 
            @RequestBody UpdateCustomerRequest request,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId) {
        
        log.info("Actualizando cliente con ID: {}", id);
        
        UUID userUuid = userId != null ? UUID.fromString(userId) : null;
        request.setId(id);
        UpdateCustomerCommand command = UpdateCustomerCommand.fromRequest(request, userUuid);
        UpdateCustomerMessage response = mediator.send(command);
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un cliente", 
               description = "Marca un cliente como inactivo (eliminación lógica)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", 
                     description = "Cliente eliminado correctamente"),
        @ApiResponse(responseCode = "404", 
                     description = "Cliente no encontrado",
                     content = @Content)
    })
    public ResponseEntity<Void> deleteCustomer(
            @Parameter(description = "ID del cliente a eliminar", required = true) 
            @PathVariable UUID id) {
        
        log.info("Eliminando cliente con ID: {}", id);
        
        DeleteCustomerCommand command = new DeleteCustomerCommand(id);
        mediator.send(command);
        
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/search")
    @Operation(summary = "Búsqueda avanzada de clientes", 
               description = "Busca clientes con filtros avanzados y paginación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Búsqueda exitosa",
                     content = @Content(mediaType = "application/json", 
                                       schema = @Schema(implementation = PaginatedResponse.class)))
    })
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        log.info("Realizando búsqueda avanzada de clientes");
        
        Pageable pageable = PageableUtil.createPageable(request);
        SearchCustomerAdvancedQuery query = new SearchCustomerAdvancedQuery(
                pageable, 
                request.getFilter(), 
                request.getQuery()
        );
        
        PaginatedResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }
}