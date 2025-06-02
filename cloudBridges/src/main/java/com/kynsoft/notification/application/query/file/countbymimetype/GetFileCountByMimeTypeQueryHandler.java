package com.kynsoft.notification.application.query.file.countbymimetype;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.notification.domain.dto.FileMimeTypeCountDto;
import com.kynsoft.notification.domain.service.IAFileService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetFileCountByMimeTypeQueryHandler implements IQueryHandler<GetFileCountByMimeTypeQuery, FileCountByMimeTypeResponse> {

    private final IAFileService fileService;

    public GetFileCountByMimeTypeQueryHandler(IAFileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public FileCountByMimeTypeResponse handle(GetFileCountByMimeTypeQuery query) {
        // Obtener estadísticas por MIME type
        List<FileMimeTypeCountDto> mimeTypeStats = fileService.getFileMimeTypeCount(query.getBusinessId());
        
        // Obtener conteo total de archivos para esta empresa
        Long totalCount = fileService.countByPath(query.getBusinessId()).getTotalCount();
        
        // Obtener tamaño total en GB para esta empresa
        Double totalSizeGB = fileService.calculateTotalDiskSpaceInGB(query.getBusinessId());
        
        // Crear y devolver la respuesta
        return new FileCountByMimeTypeResponse(
                query.getBusinessId(),
                mimeTypeStats,
                totalCount,
                totalSizeGB
        );
    }
}