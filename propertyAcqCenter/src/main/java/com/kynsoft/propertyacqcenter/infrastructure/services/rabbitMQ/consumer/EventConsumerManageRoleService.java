package com.kynsoft.propertyacqcenter.infrastructure.services.rabbitMQ.consumer;


import com.kynsoft.propertyacqcenter.domain.dto.ManageRolDto;
import com.kynsoft.propertyacqcenter.domain.services.IManageRoleService;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.ManageRoleReadDataJPARepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerManageRoleService {
    private final IManageRoleService service;
    private final ManageRoleReadDataJPARepository query;

    public EventConsumerManageRoleService(IManageRoleService service, ManageRoleReadDataJPARepository query) {
        this.service = service;
        this.query = query;
    }

    @RabbitListener(queues = "manage.role.queue.property.acq.center")
    public void handleCompanyEvent(ManageRolDto event) {
        boolean isRole = query.findById(event.getId()).isPresent();
        if(isRole){
            this.service.update(event);
        }else{
            this.service.create(event);
        }
    }
}
