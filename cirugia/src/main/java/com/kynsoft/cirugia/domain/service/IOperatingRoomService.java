package com.kynsoft.cirugia.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.cirugia.domain.dto.OperatingRoom;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IOperatingRoomService {
    // Métodos básicos CRUD
    UUID createOperatingRoom(OperatingRoom operatingRoom);
    void updateOperatingRoom(OperatingRoom operatingRoom);
    void updateStatus(UUID roomId, String status, UUID updatedBy);
    void deleteOperatingRoom(UUID id);
    
    // Métodos de consulta
    Optional<OperatingRoom> getOperatingRoomById(UUID id);
    List<OperatingRoom> listOperatingRoomsByBusiness(UUID businessId);
    List<OperatingRoom> listOperatingRoomsByStatus(String status);
    List<OperatingRoom> listOperatingRoomsByType(String type);
    List<OperatingRoom> listAvailableOperatingRooms(UUID businessId);
    List<OperatingRoom> listOperatingRoomsByFloor(String floor, UUID businessId);
    List<OperatingRoom> listOperatingRoomsByWing(String wing, UUID businessId);
    
    // Método de búsqueda paginada
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}