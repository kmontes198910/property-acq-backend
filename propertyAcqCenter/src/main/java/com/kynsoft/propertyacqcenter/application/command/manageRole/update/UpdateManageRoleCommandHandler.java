package com.kynsoft.propertyacqcenter.application.command.manageRole.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.DocumentTypeDto;
import com.kynsoft.propertyacqcenter.domain.dto.ManageRolDto;
import com.kynsoft.propertyacqcenter.domain.services.IDocumentTypeService;
import com.kynsoft.propertyacqcenter.domain.services.IManageRoleService;
import java.util.List;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UpdateManageRoleCommandHandler implements ICommandHandler<UpdateManageRoleCommand> {

    private final IDocumentTypeService documentTypeService;

    private final IManageRoleService roleService;

    public UpdateManageRoleCommandHandler(IDocumentTypeService documentTypeService,
                                        IManageRoleService roleService) {
        this.documentTypeService = documentTypeService;
        this.roleService = roleService;
    }

    @Override
    public void handle(UpdateManageRoleCommand command) {
        ManageRolDto rol = this.roleService.findById(command.getRol());
        rol.setDocumentTypes(get(command.getDocumentTypes()));
        this.roleService.update(rol);
    }

    private List<DocumentTypeDto> get(List<UUID> ids) {
        return ids.stream()
            .map(this.documentTypeService::findById)
            .collect(Collectors.toList());
    }

}
