package com.kynsoft.propertyacqcenter.application.command.employee.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.utils.ConsumerUpdate;
import com.kynsof.share.utils.UpdateIfNotNull;
import com.kynsoft.propertyacqcenter.domain.dto.EmployeeDto;
import com.kynsoft.propertyacqcenter.domain.services.IBusinessService;
import com.kynsoft.propertyacqcenter.domain.services.IEmployeeService;
import com.kynsoft.propertyacqcenter.infrastructure.services.rabbitMQ.dto.RabbitMqEmployeeDto;
import com.kynsoft.propertyacqcenter.infrastructure.services.rabbitMQ.eventPublisher.EventEmployeePublisherService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Consumer;

@Component
public class UpdateEmployeeCommandHandler implements ICommandHandler<UpdateEmployeeCommand> {

    private final IEmployeeService employeeService;

    private final IBusinessService businessService;

    private final EventEmployeePublisherService eventEmployeePublisherService;

    public UpdateEmployeeCommandHandler(IEmployeeService employeeService, IBusinessService businessService, EventEmployeePublisherService eventEmployeePublisherService) {
        this.employeeService = employeeService;
        this.businessService = businessService;
        this.eventEmployeePublisherService = eventEmployeePublisherService;
    }

    @Override
    @Transactional
    public void handle(UpdateEmployeeCommand command) {
        this.employeeService.validateEmail(command.getEmail(), command.getId());

        EmployeeDto employeeDto = employeeService.findById(command.getId());

        ConsumerUpdate update = new ConsumerUpdate();

        UpdateIfNotNull.updateIfStringNotNullNotEmptyAndNotEquals(employeeDto::setFirstName, command.getFirstName(), employeeDto.getFirstName(), update::setUpdate);
        UpdateIfNotNull.updateIfStringNotNullNotEmptyAndNotEquals(employeeDto::setLastName, command.getLastName(), employeeDto.getLastName(), update::setUpdate);
        UpdateIfNotNull.updateIfStringNotNullNotEmptyAndNotEquals(employeeDto::setPhoneNumber, command.getPhoneNumber(), employeeDto.getPhoneNumber(), update::setUpdate);
        UpdateIfNotNull.updateIfStringNotNullNotEmptyAndNotEquals(employeeDto::setEmail, command.getEmail(), employeeDto.getEmail(), update::setUpdate);
        UpdateIfNotNull.updateBoolean(employeeDto::setActive, command.getActive(), employeeDto.getActive(), update::setUpdate);

        employeeDto.setHireDate(command.getHireDate());
        employeeDto.setDepartment(command.getDepartment());
        employeeDto.setSalary(command.getSalary());
        employeeDto.setPosition(command.getPosition());
        updateEntity(employeeDto::setBusiness, command.getBusiness(), employeeDto.getBusiness() != null ? employeeDto.getBusiness().getId() : null, businessService::findById, update::setUpdate);

        employeeService.update(employeeDto);
        this.eventEmployeePublisherService.publishRecoveryBedEvent(
                new RabbitMqEmployeeDto(
                        employeeDto.getId(),
                        employeeDto.getFirstName(),
                        employeeDto.getLastName(),
                        employeeDto.getEmail(),
                        employeeDto.getBusiness().getId()
                )
        );
    }

    private <T> void updateEntity(Consumer<T> setter, UUID newValue, UUID oldValue, EntityFinder<T> finder, Consumer<Integer> update) {
        if (newValue != null && !newValue.equals(oldValue)) {
            T entity = finder.findById(newValue);
            setter.accept(entity);
            update.accept(1);
        }
    }

    @FunctionalInterface
    private interface EntityFinder<T> {

        T findById(UUID id);
    }
}
