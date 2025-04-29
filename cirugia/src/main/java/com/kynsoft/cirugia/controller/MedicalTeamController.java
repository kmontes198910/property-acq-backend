package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.cirugia.application.command.medicalteam.create.CreateMedicalTeamCommand;
import com.kynsoft.cirugia.application.command.medicalteam.create.CreateMedicalTeamRequest;
import com.kynsoft.cirugia.application.command.medicalteam.delete.DeleteMedicalTeamCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/medical-teams")
@RequiredArgsConstructor
public class MedicalTeamController {

    private final IMediator mediator;

    /**
     * Crea un nuevo miembro del equipo médico
     * @param request Datos del miembro del equipo médico a crear
     * @return Respuesta con status 201 (Created) y el ID del miembro creado
     */
    @PostMapping
    public ResponseEntity<UUID> createMedicalTeam(@RequestBody CreateMedicalTeamRequest request) {
        CreateMedicalTeamCommand command = CreateMedicalTeamCommand.fromRequest(request);
        mediator.send(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(command.getId());
    }
    
    /**
     * Elimina un miembro del equipo médico
     * @param id ID del miembro del equipo médico a eliminar
     * @return Respuesta con status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalTeam(@PathVariable UUID id) {
        DeleteMedicalTeamCommand command = new DeleteMedicalTeamCommand(id);
        mediator.send(command);
        return ResponseEntity.noContent().build();
    }
}