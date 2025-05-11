package com.kynsoft.invoiceservice.application.query.customer.get;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.invoiceservice.domain.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCustomerByIdQueryHandler implements IQueryHandler<GetCustomerByIdQuery, CustomerResponse> {

    private final ICustomerService customerService;

    @Override
    public CustomerResponse handle(GetCustomerByIdQuery query) {
        // Utilizar el servicio para buscar el cliente por ID
        CustomerDto customerDto = customerService.findById(query.getId());
        
        // Mapear el DTO a la respuesta
        return mapDtoToResponse(customerDto);
    }
    
    private CustomerResponse mapDtoToResponse(CustomerDto dto) {
        return CustomerResponse.builder()
                .id(dto.getId())
                .identificationType(dto.getIdentificationType())
                .identificationNumber(dto.getIdentificationNumber())
                .businessName(dto.getBusinessName())
                .address(dto.getAddress())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .isActive(dto.getIsActive())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}