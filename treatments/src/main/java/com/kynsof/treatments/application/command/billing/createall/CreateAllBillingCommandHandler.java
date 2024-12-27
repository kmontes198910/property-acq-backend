package com.kynsof.treatments.application.command.billing.createall;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.application.command.billing.create.CreateBillingRequest;
import com.kynsof.treatments.domain.dto.MedicinesDto;
import com.kynsof.treatments.domain.service.IMedicinesService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateAllBillingCommandHandler implements ICommandHandler<CreateAllBillingCommand> {

    private final IMedicinesService serviceImpl;

    public CreateAllBillingCommandHandler(IMedicinesService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(CreateAllBillingCommand command) {
//        for (CreateBillingRequest createMedicineRequest : command.getPayload()) {
//            MedicinesDto create = new MedicinesDto(
//                    UUID.randomUUID(),
//                    createMedicineRequest.getName(),
//                    createMedicineRequest.getPresentation()
//            );
//            try {
//                this.serviceImpl.create(create);
//            } catch (Exception e) {
//            }
//        }

    }
}
