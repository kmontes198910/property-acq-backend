package com.kynsof.identity.application.query.users.getPermissionByIdUser;

import com.kynsof.identity.domain.dto.UserSystemDto;
import com.kynsof.identity.domain.interfaces.service.IUserSystemService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class FindPermissionsByIdUserQueryHandler implements IQueryHandler<FindPermissionsByIdUserQuery, PermissionsByIdUserResponse>  {

    private final IUserSystemService serviceImpl;

    public FindPermissionsByIdUserQueryHandler(IUserSystemService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PermissionsByIdUserResponse handle(FindPermissionsByIdUserQuery query) {
        UserSystemDto userSystemDto = serviceImpl.findById(query.getId());

        return new PermissionsByIdUserResponse(userSystemDto);
    }
}
