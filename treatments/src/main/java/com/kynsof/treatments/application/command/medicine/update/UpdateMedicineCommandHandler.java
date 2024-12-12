package com.kynsof.treatments.application.command.medicine.update;

import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.utils.UpdateIfNotNull;
import com.kynsof.treatments.domain.dto.MedicinesDto;
import com.kynsof.treatments.domain.rules.medicines.MedicinesNameMustBeUniqueRule;
import com.kynsof.treatments.domain.service.IMedicinesService;
import org.springframework.stereotype.Component;

@Component
public class UpdateMedicineCommandHandler implements ICommandHandler<UpdateMedicineCommand> {

    private final IMedicinesService serviceImpl;

    public UpdateMedicineCommandHandler(IMedicinesService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(UpdateMedicineCommand command) {
        RulesChecker.checkRule(new MedicinesNameMustBeUniqueRule(this.serviceImpl, command.getName(), command.getId()));
        MedicinesDto update = this.serviceImpl.findById(command.getId());

        UpdateIfNotNull.updateIfStringNotNull(update::setName, command.getName());
        UpdateIfNotNull.updateIfStringNotNull(update::setPresentation, command.getPresentation());

        this.serviceImpl.update(update);
    }
}
