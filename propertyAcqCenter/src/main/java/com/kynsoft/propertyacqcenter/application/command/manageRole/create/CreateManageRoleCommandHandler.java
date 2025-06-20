package com.kynsoft.propertyacqcenter.application.command.manageRole.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.DocumentTypeDto;
import com.kynsoft.propertyacqcenter.domain.dto.ManageRolDto;
import com.kynsoft.propertyacqcenter.domain.services.IDocumentTypeService;
import com.kynsoft.propertyacqcenter.domain.services.IManageRoleService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CreateManageRoleCommandHandler implements ICommandHandler<CreateManageRoleCommand> {

    private final IManageRoleService service;
    private final IDocumentTypeService documentTypeService;

    public CreateManageRoleCommandHandler(IManageRoleService service, IDocumentTypeService documentTypeService) {
        this.service = service;
        this.documentTypeService = documentTypeService;
    }

    @Override
    public void handle(CreateManageRoleCommand command) {
        ManageRolDto dto = new ManageRolDto(command.getId(), command.getCode(), command.getName(), false);
        dto.setDocumentTypes(get(command.getDocumentTypes()));
        service.create(dto);
    }

    private List<DocumentTypeDto> get(List<UUID> ids) {
        return ids.stream()
            .map(this.documentTypeService::findById)
            .collect(Collectors.toList());
    }
}
