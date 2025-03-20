//package com.kynsof.calendar.application.command.serviceType.update;
//
//import com.kynsof.calendar.domain.dto.ServiceTypeDto;
//import com.kynsof.calendar.domain.service.IServiceTypeService;
//import com.kynsof.calendar.infrastructure.service.kafka.producer.typeService.ProducerServiceTypeEventService;
//import com.kynsof.share.core.domain.RulesChecker;
//import com.kynsof.share.core.domain.bus.command.ICommandHandler;
//import com.kynsof.share.core.domain.kafka.entity.ServiceTypeKafka;
//import com.kynsof.share.core.domain.rules.ValidateObjectNotNullRule;
//import com.kynsof.share.utils.UpdateIfNotNull;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UpdateServiceTypeCommandHandler implements ICommandHandler<UpdateServiceTypeCommand> {
//
//    private final IServiceTypeService service;
//    private final ProducerServiceTypeEventService producerServiceTypeEventService;
//
//    public UpdateServiceTypeCommandHandler(IServiceTypeService service,
//                                           ProducerServiceTypeEventService producerServiceTypeEventService) {
//        this.service = service;
//        this.producerServiceTypeEventService = producerServiceTypeEventService;
//    }
//
//    @Override
//    public void handle(UpdateServiceTypeCommand command) {
//        RulesChecker.checkRule(new ValidateObjectNotNullRule<>(command.getId(), "id", "Service Type ID cannot be null."));
//
//        ServiceTypeDto update = this.service.findById(command.getId());
//
//
//        UpdateIfNotNull.updateIfStringNotNull(update::setName, command.getName());
//        update.setPicture(command.getPicture());
//        update.setStatus(command.getStatus());
//        service.update(update);
//        this.producerServiceTypeEventService.create(new ServiceTypeKafka(update.getId(), update.getName(), update.getPicture(), update.getStatus().name(), update.getCode()));
//    }
//}
