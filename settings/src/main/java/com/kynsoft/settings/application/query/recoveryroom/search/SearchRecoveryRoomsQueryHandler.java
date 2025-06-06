package com.kynsoft.settings.application.query.recoveryroom.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.settings.domain.services.IRecoveryRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchRecoveryRoomsQueryHandler implements IQueryHandler<SearchRecoveryRoomsQuery, PaginatedResponse> {

    private final IRecoveryRoomService recoveryRoomService;

    @Override
    public PaginatedResponse handle(SearchRecoveryRoomsQuery query) {
        log.info("Buscando salas de recuperación con criterios de filtrado: {}", query.getFilter());

        return recoveryRoomService.search(query.getPageable(), query.getFilter());
    }
}