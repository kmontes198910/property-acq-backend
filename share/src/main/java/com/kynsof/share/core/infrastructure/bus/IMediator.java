package com.kynsof.share.core.infrastructure.bus;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsof.share.core.domain.bus.query.IQuery;
import com.kynsof.share.core.domain.bus.query.IResponse;
import reactor.core.publisher.Mono;

public interface IMediator {
    // Reactive methods for WebFlux compatibility
    <M extends ICommandMessage> Mono<M> sendAsync(ICommand command);
    
    <R extends IResponse> Mono<R> sendAsync(IQuery query);
    
    // Original methods for backward compatibility
    <M extends ICommandMessage> M send(ICommand command);
    
    <R extends IResponse> R send(IQuery query);
}
