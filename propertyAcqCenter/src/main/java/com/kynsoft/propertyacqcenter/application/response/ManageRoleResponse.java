package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.DocumentTypeDto;
import com.kynsoft.propertyacqcenter.domain.dto.ManageRolDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ManageRoleResponse implements IResponse {
    private UUID id;
    private String code;
    private String name;
    private List<DocumentTypeResponse> documentTypes;

    public ManageRoleResponse(ManageRolDto dto) {
        this.id = dto.getId();
        this.code = dto.getCode();
        this.name = dto.getName();
        this.documentTypes = get(dto.getDocumentTypes());
    }

    private List<DocumentTypeResponse> get(List<DocumentTypeDto> ids) {
        return ids.stream()
                .map(DocumentTypeResponse::new)
                .collect(Collectors.toList());
    }

}
