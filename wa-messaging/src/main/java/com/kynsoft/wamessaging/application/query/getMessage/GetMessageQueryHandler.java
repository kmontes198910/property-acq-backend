package com.kynsoft.wamessaging.application.query.getMessage;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.wamessaging.application.request.MessageResponse;
import com.kynsoft.wamessaging.application.service.MessageCoordinatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetMessageQueryHandler implements IQueryHandler<GetMessageQuery, GetMessageResponse> {

    private final MessageCoordinatorService messageCoordinatorService;

    @Override
    public GetMessageResponse handle(GetMessageQuery query) {
        log.info("Manejando consulta de mensaje con ID: {}", query.getId());
        
        MessageResponse message = messageCoordinatorService.getMessage(query.getId());
        
        return new GetMessageResponse(message);
    }
}
