package com.kynsof.identity.application.command.user.update;

import com.kynsof.identity.application.command.auth.registry.UserRequest;
import com.kynsof.identity.domain.dto.UserSystemDto;
import com.kynsof.identity.domain.interfaces.service.IAuthService;
import com.kynsof.identity.domain.interfaces.service.IUserSystemService;
import com.kynsof.identity.domain.rules.usersystem.ModuleEmailMustBeUniqueRule;
import com.kynsof.identity.domain.rules.usersystem.ModuleUserNameMustBeUniqueRule;
import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.rules.ValidateObjectNotNullRule;
import com.kynsof.share.utils.UpdateIfNotNull;
import org.springframework.stereotype.Component;

@Component
public class UpdateUserSystemCommandHandler implements ICommandHandler<UpdateUserSystemCommand> {

    private final IUserSystemService systemService;
    private final IAuthService keycloakProvider;

    public UpdateUserSystemCommandHandler(IUserSystemService systemService,
                                          IAuthService keycloakProvider) {
        this.systemService = systemService;
        this.keycloakProvider = keycloakProvider;
    }

    @Override
    public void handle(UpdateUserSystemCommand command) {
        // Validación inicial
        RulesChecker.checkRule(new ValidateObjectNotNullRule<>(command.getId(), "id", "UserSystem ID cannot be null."));

        // Recuperación del usuario a actualizar
        UserSystemDto objectToUpdate = this.systemService.findById(command.getId());

        boolean isPublish = false;
        boolean idUpdate = false;

        // Actualizaciones de atributos con validaciones de unicidad y reglas de negocio
        isPublish |= updateEmailIfChanged(command, objectToUpdate);
        isPublish |= updateUserNameIfChanged(command, objectToUpdate);
        isPublish |= updateNameIfChanged(command, objectToUpdate);
        isPublish |= updateLastNameIfChanged(command, objectToUpdate);

        // Actualizaciones sin reglas adicionales
        idUpdate |= updateUserTypeIfChanged(command, objectToUpdate);
        idUpdate |= updateImageIfChanged(command, objectToUpdate);

        // Publicar el evento de actualización si es necesario

        // Guardar los cambios en el sistema y actualizar en Keycloak si hubo modificaciones relevantes
        if (idUpdate) {
            systemService.update(objectToUpdate);
            updateUserKeycloak(command, objectToUpdate.getKeyCloakId().toString());
        }
    }

    private boolean updateEmailIfChanged(UpdateUserSystemCommand command, UserSystemDto objectToUpdate) {
        if (command.getEmail() != null && !command.getEmail().isEmpty() && !command.getEmail().equals(objectToUpdate.getEmail())) {
            UpdateIfNotNull.updateIfNotNull(objectToUpdate::setEmail, command.getEmail());
            RulesChecker.checkRule(new ModuleEmailMustBeUniqueRule(systemService, command.getEmail(), objectToUpdate.getId()));
            return true;
        }
        return false;
    }

    private boolean updateUserNameIfChanged(UpdateUserSystemCommand command, UserSystemDto objectToUpdate) {
        if (command.getUserName() != null && !command.getUserName().isEmpty() && !command.getUserName().equals(objectToUpdate.getUserName())) {
            UpdateIfNotNull.updateIfNotNull(objectToUpdate::setUserName, command.getUserName());
            RulesChecker.checkRule(new ModuleUserNameMustBeUniqueRule(systemService, command.getUserName(), objectToUpdate.getId()));
            return true;
        }
        return false;
    }

    private boolean updateNameIfChanged(UpdateUserSystemCommand command, UserSystemDto objectToUpdate) {
        if (command.getName() != null && !command.getName().isEmpty() && !command.getName().equals(objectToUpdate.getName())) {
            UpdateIfNotNull.updateIfNotNull(objectToUpdate::setName, command.getName());
            return true;
        }
        return false;
    }

    private boolean updateLastNameIfChanged(UpdateUserSystemCommand command, UserSystemDto objectToUpdate) {
        if (command.getLastName() != null && !command.getLastName().isEmpty() && !command.getLastName().equals(objectToUpdate.getLastName())) {
            UpdateIfNotNull.updateIfNotNull(objectToUpdate::setLastName, command.getLastName());
            return true;
        }
        return false;
    }

    private boolean updateImageIfChanged(UpdateUserSystemCommand command, UserSystemDto objectToUpdate) {
        if (command.getImage() == null || !command.getImage().equals(objectToUpdate.getImage())) {
            objectToUpdate.setImage(command.getImage());
            return true;
        }
        return false;
    }

    private boolean updateUserTypeIfChanged(UpdateUserSystemCommand command, UserSystemDto objectToUpdate) {
        if (command.getUserType() != null && command.getUserType() != objectToUpdate.getUserType()) {
            UpdateIfNotNull.updateIfNotNull(objectToUpdate::setUserType, command.getUserType());
            return true;
        }
        return false;
    }

    private void updateUserKeycloak(UpdateUserSystemCommand command, String userKeycloakId) {
        UserRequest userRequest = new UserRequest(
                command.getUserName(),
                command.getEmail(),
                command.getName(),
                command.getLastName(),
                ""
        );
        keycloakProvider.updateUser(userKeycloakId, userRequest);
    }
}