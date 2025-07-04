package com.kynsof.identity.application.query.users.getPermissionByIdUser;

import com.kynsof.identity.domain.dto.PermissionDto;
import com.kynsof.identity.domain.interfaces.service.IUsersRolesService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FindPermissionsByIdUserQueryHandler implements IQueryHandler<FindPermissionsByIdUserQuery, RolesPermissionsUserResponse>  {

    private final IUsersRolesService serviceImpl;

    public FindPermissionsByIdUserQueryHandler(IUsersRolesService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public RolesPermissionsUserResponse handle(FindPermissionsByIdUserQuery query) {
        List<PermissionDto> userSystemDto = serviceImpl.permissions(query.getId());

        return new RolesPermissionsUserResponse(userSystemDto);
    }
}
