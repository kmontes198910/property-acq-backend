package com.kynsof.identity.infrastructure.services.rabbitMq.consumer;


import com.kynsof.identity.domain.dto.UserStatus;
import com.kynsof.identity.domain.dto.UserSystemDto;
import com.kynsof.identity.domain.interfaces.service.IUserSystemService;
import com.kynsof.identity.infrastructure.services.rabbitMq.dto.RabbitMqEmployeeDto;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerEmployeeService {
    private final IUserSystemService service;

    public EventConsumerEmployeeService(IUserSystemService service) {
        this.service = service;
    }

    @RabbitListener(queues = "employee.queue.identity")
    public void handleCompanyEvent(RabbitMqEmployeeDto event) {
        UserSystemDto userSystemDto = new UserSystemDto(
                event.getId(),"", event.getEmail(),
                event.getFirstName(), event.getLastName(),
                UserStatus.ACTIVE,null

        );
        userSystemDto.setSelectedBusiness(event.getBusiness());
        try {
            service.findById(event.getId()); // Lanza excepción si no existe
            service.update(userSystemDto);
        } catch (BusinessNotFoundException e) {
            service.create(userSystemDto);
        }
    }

}
