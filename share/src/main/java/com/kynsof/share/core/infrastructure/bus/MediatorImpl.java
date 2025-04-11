    package com.kynsof.share.core.infrastructure.bus;

    import com.kynsof.share.core.domain.bus.command.ICommand;
    import com.kynsof.share.core.domain.bus.command.ICommandMessage;
    import com.kynsof.share.core.domain.bus.query.IQuery;
    import com.kynsof.share.core.domain.bus.query.IResponse;
    import com.kynsof.share.core.infrastructure.bus.command.InMemoryCommandBus;
    import com.kynsof.share.core.infrastructure.bus.query.InMemoryQueryBus;
    import org.springframework.stereotype.Component;
    import reactor.core.publisher.Mono;
    import reactor.core.scheduler.Schedulers;

    @Component
    public class MediatorImpl implements IMediator {

        private final InMemoryCommandBus commandBus;
        private final InMemoryQueryBus queryBus;

        public MediatorImpl(InMemoryCommandBus commandBus, InMemoryQueryBus queryBus) {
            this.commandBus = commandBus;
            this.queryBus = queryBus;
        }

        @Override
        public <M extends ICommandMessage> Mono<M> sendAsync(ICommand command) {
            return Mono.fromCallable(() -> {
                commandBus.dispatch(command);
                return (M) command.getMessage();
            }).subscribeOn(Schedulers.boundedElastic());
        }

        @Override
        public <R extends IResponse> Mono<R> sendAsync(IQuery query) {
            return Mono.fromCallable(() -> (R) queryBus.ask(query))
                    .subscribeOn(Schedulers.boundedElastic());
        }

        @Override
        public <M extends ICommandMessage> M send(ICommand command) {
            commandBus.dispatch(command);
            return (M) command.getMessage();
        }

        @Override
        public <R extends IResponse> R send(IQuery query) {
            return (R) queryBus.ask(query);
        }
    }
