package com.kynsoft.propertyacqcenter.controller;

import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/legal-entities")
public class LegalEntityController {

    private final ILegalEntityService legalEntityService;

    public LegalEntityController(ILegalEntityService legalEntityService) {
        this.legalEntityService = legalEntityService;
    }

    @PostMapping
    public ResponseEntity<UUID> create(@RequestBody LegalEntityDto legalEntityDto, 
                                      @RequestHeader(value = "X-User-Id", required = false) String userId,
                                      @RequestHeader(value = "X-User-Name", required = false) String username) {
        
        // Asignar el creador de la entidad si está disponible
        if (userId != null && !userId.isEmpty()) {
            try {
                legalEntityDto.setCreatedBy(UUID.fromString(userId));
            } catch (IllegalArgumentException e) {
                // En caso de que el userId no sea un UUID válido
                // Simplemente continuar sin establecer el creador
            }
        }
        
        // También podemos almacenar el nombre de usuario como información adicional si se necesita
        // Por ejemplo, podríamos agregarlo a las notas o a un campo específico
        
        UUID id = legalEntityService.create(legalEntityDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody LegalEntityDto legalEntityDto) {
        legalEntityDto.setId(id);
        legalEntityService.update(legalEntityDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        legalEntityService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LegalEntityDto> getById(@PathVariable UUID id) {
        LegalEntityDto legalEntityDto = legalEntityService.findById(id);
        if (legalEntityDto != null) {
            return ResponseEntity.ok(legalEntityDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tax-id/{taxId}")
    public ResponseEntity<LegalEntityDto> getByTaxId(@PathVariable String taxId) {
        LegalEntityDto legalEntityDto = legalEntityService.findByTaxId(taxId);
        if (legalEntityDto != null) {
            return ResponseEntity.ok(legalEntityDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<LegalEntityDto>> search(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(required = false) List<Object> filters) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        List<LegalEntityDto> legalEntities = legalEntityService.search(pageable, filters);
        return ResponseEntity.ok(legalEntities);
    }
}
