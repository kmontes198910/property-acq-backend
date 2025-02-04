package com.kynsoft.report.applications.command.dbconection.update;

import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.utils.ConsumerUpdate;
import com.kynsof.share.utils.UpdateIfNotNull;
import com.kynsoft.report.domain.dto.DBConnectionDto;
import com.kynsoft.report.domain.dto.status.Status;
import com.kynsoft.report.domain.rules.dbconection.DBConectionCodeMustBeUniqueRule;
import com.kynsoft.report.domain.services.IDBConnectionService;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class UpdateDBConectionCommandHandler implements ICommandHandler<UpdateDBConectionCommand> {

    private final IDBConnectionService service;

    public UpdateDBConectionCommandHandler(IDBConnectionService service) {
        this.service = service;
    }

    @Override
    public void handle(UpdateDBConectionCommand command) {
        RulesChecker.checkRule(new DBConectionCodeMustBeUniqueRule(this.service, command.getCode(), command.getId()));

        DBConnectionDto dto = this.service.findById(command.getId());
        ConsumerUpdate update = new ConsumerUpdate();

        UpdateIfNotNull.updateIfStringNotNullNotEmptyAndNotEquals(dto::setUrl, command.getUrl(), dto.getUrl(), update::setUpdate);
        UpdateIfNotNull.updateIfStringNotNullNotEmptyAndNotEquals(dto::setUsername, command.getUsername(), dto.getUsername(), update::setUpdate);
        UpdateIfNotNull.updateIfStringNotNullNotEmptyAndNotEquals(dto::setPassword, command.getPassword(), dto.getPassword(), update::setUpdate);
        UpdateIfNotNull.updateIfStringNotNullNotEmptyAndNotEquals(dto::setName, command.getName(), dto.getName(), update::setUpdate);
        UpdateIfNotNull.updateIfStringNotNullNotEmptyAndNotEquals(dto::setCode, command.getCode(), dto.getCode(), update::setUpdate);
        updateStatus(dto::setStatus, command.getStatus(), dto.getStatus(), update::setUpdate);

        if(update.getUpdate() > 0){
            this.service.update(dto);
        }
    }

    private void updateStatus(Consumer<Status> setter, Status newValue, Status oldValue, Consumer<Integer> update) {
        if (newValue != null && !newValue.equals(oldValue)) {
            setter.accept(newValue);
            update.accept(1);
        }
    }
}
