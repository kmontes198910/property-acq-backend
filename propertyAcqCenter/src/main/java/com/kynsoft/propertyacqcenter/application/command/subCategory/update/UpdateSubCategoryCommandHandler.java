package com.kynsoft.propertyacqcenter.application.command.subCategory.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryDto;
import com.kynsoft.propertyacqcenter.domain.services.ISubCategoryService;
import org.springframework.stereotype.Component;

@Component
public class UpdateSubCategoryCommandHandler implements ICommandHandler<UpdateSubCategoryCommand> {

    private final ISubCategoryService subCategoryService;

    public UpdateSubCategoryCommandHandler(ISubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @Override
    public void handle(UpdateSubCategoryCommand command) {
        this.subCategoryService.create(new SubCategoryDto(
                command.getId(), 
                command.getName(),
                command.getCode(),
                command.getDescription(),
                command.getCategory(),
                command.getCompanyType()
        ));
    }
}
