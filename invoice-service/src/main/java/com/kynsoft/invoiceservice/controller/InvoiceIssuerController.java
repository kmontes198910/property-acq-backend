package com.kynsoft.invoiceservice.controller;

import com.kynsoft.invoiceservice.dto.InvoiceIssuerDTO;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceIssuer;
import com.kynsoft.invoiceservice.infrastructure.repository.query.InvoiceIssuerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/invoice-issuers")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Emisores de Facturas", description = "API para gestionar emisores de facturas")
public class InvoiceIssuerController {

    private final InvoiceIssuerRepository invoiceIssuerRepository;

    @Operation(summary = "Listar todos los emisores de facturas", 
               description = "Obtiene una lista de todos los emisores de facturas registrados")
    @ApiResponse(responseCode = "200", 
                 description = "Lista de emisores recuperada correctamente",
                 content = @Content(mediaType = "application/json", 
                                   schema = @Schema(implementation = InvoiceIssuerDTO.class)))
    @GetMapping
    public ResponseEntity<List<InvoiceIssuerDTO>> getAllIssuers() {
        List<InvoiceIssuerDTO> issuers = invoiceIssuerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(issuers);
    }

    @Operation(summary = "Obtener emisor por ID", 
               description = "Recupera un emisor de facturas específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Emisor encontrado",
                     content = @Content(mediaType = "application/json", 
                                       schema = @Schema(implementation = InvoiceIssuerDTO.class))),
        @ApiResponse(responseCode = "404", 
                     description = "Emisor no encontrado",
                     content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceIssuerDTO> getIssuerById(
            @Parameter(description = "ID del emisor a buscar", required = true) 
            @PathVariable UUID id) {
        Optional<InvoiceIssuer> issuer = invoiceIssuerRepository.findById(id);
        return issuer.map(value -> ResponseEntity.ok(convertToDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo emisor de facturas", 
               description = "Registra un nuevo emisor de facturas en el sistema")
    @ApiResponse(responseCode = "201", 
                 description = "Emisor creado correctamente",
                 content = @Content(mediaType = "application/json", 
                                   schema = @Schema(implementation = InvoiceIssuerDTO.class)))
    @PostMapping
    public ResponseEntity<InvoiceIssuerDTO> createIssuer(
            @Parameter(description = "Datos del emisor a crear", required = true) 
            @RequestBody InvoiceIssuerDTO issuerDTO) {
        InvoiceIssuer issuer = convertToEntity(issuerDTO);
        InvoiceIssuer savedIssuer = invoiceIssuerRepository.save(issuer);
        return new ResponseEntity<>(convertToDTO(savedIssuer), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar emisor de facturas", 
               description = "Actualiza la información de un emisor de facturas existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Emisor actualizado correctamente",
                     content = @Content(mediaType = "application/json", 
                                       schema = @Schema(implementation = InvoiceIssuerDTO.class))),
        @ApiResponse(responseCode = "404", 
                     description = "Emisor no encontrado",
                     content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceIssuerDTO> updateIssuer(
            @Parameter(description = "ID del emisor a actualizar", required = true) 
            @PathVariable UUID id,
            @Parameter(description = "Datos actualizados del emisor", required = true) 
            @RequestBody InvoiceIssuerDTO issuerDTO) {
        if (!invoiceIssuerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        InvoiceIssuer issuer = convertToEntity(issuerDTO);
        issuer.setId(id);
        InvoiceIssuer updatedIssuer = invoiceIssuerRepository.save(issuer);
        return ResponseEntity.ok(convertToDTO(updatedIssuer));
    }

    @Operation(summary = "Eliminar emisor de facturas", 
               description = "Elimina un emisor de facturas existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", 
                     description = "Emisor eliminado correctamente",
                     content = @Content),
        @ApiResponse(responseCode = "404", 
                     description = "Emisor no encontrado",
                     content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssuer(
            @Parameter(description = "ID del emisor a eliminar", required = true) 
            @PathVariable UUID id) {
        if (!invoiceIssuerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        invoiceIssuerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private InvoiceIssuerDTO convertToDTO(InvoiceIssuer issuer) {
        return InvoiceIssuerDTO.builder()
                .id(issuer.getId())
                .ruc(issuer.getRuc())
                .businessName(issuer.getBusinessName())
                .commercialName(issuer.getCommercialName())
                .establishment(issuer.getEstablishment())
                .emissionPoint(issuer.getEmissionPoint())
                .address(issuer.getAddress())
                .email(issuer.getEmail())
                .phone(issuer.getPhone())
                .pointOfSale(issuer.getPointOfSale())
                .specialTaxpayer(issuer.getSpecialTaxpayer())
                .retentionAgent(issuer.getRetentionAgent())
                .rimpeRegime(issuer.getRimpeRegime())
                .logoUrl(issuer.getLogoUrl())
                .environment(issuer.getEnvironment())
                .status(issuer.getStatus())
                .build();
    }

    private InvoiceIssuer convertToEntity(InvoiceIssuerDTO dto) {
        return InvoiceIssuer.builder()
                .id(dto.getId())
                .ruc(dto.getRuc())
                .businessName(dto.getBusinessName())
                .commercialName(dto.getCommercialName())
                .establishment(dto.getEstablishment())
                .emissionPoint(dto.getEmissionPoint())
                .address(dto.getAddress())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .pointOfSale(dto.isPointOfSale())
                .specialTaxpayer(dto.getSpecialTaxpayer())
                .retentionAgent(dto.getRetentionAgent())
                .rimpeRegime(dto.getRimpeRegime())
                .logoUrl(dto.getLogoUrl())
                .environment(dto.getEnvironment())
                .status(dto.getStatus())
                .digitalCertP12(dto.getDigitalCertP12())
                .digitalCertPassword(dto.getDigitalCertPassword())
                .build();
    }
}