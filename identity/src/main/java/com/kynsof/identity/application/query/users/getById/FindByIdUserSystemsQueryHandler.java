package com.kynsof.identity.application.query.users.getById;

import com.kynsof.identity.domain.dto.ManageRolDto;
import com.kynsof.identity.domain.dto.UserSystemDto;
import com.kynsof.identity.domain.interfaces.service.IUserSystemService;
import com.kynsof.identity.domain.interfaces.service.IUsersRolesService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FindByIdUserSystemsQueryHandler implements IQueryHandler<FindByIdUserSystemsQuery, UserSystemsByIdResponse>  {

    private final IUserSystemService serviceImpl;
    private final IUsersRolesService usersRolesService;

    public FindByIdUserSystemsQueryHandler(IUserSystemService serviceImpl, IUsersRolesService usersRolesService) {
        this.serviceImpl = serviceImpl;
        this.usersRolesService = usersRolesService;
    }

    @Override
    public UserSystemsByIdResponse handle(FindByIdUserSystemsQuery query) {
        UserSystemDto userSystemDto = serviceImpl.findById(query.getId());
        List<ManageRolDto> roles = this.usersRolesService.roles(query.getId());

        UserSystemsByIdResponse response = new UserSystemsByIdResponse(userSystemDto);
        response.setManageRoles(roles);

        return response;
    }
}
