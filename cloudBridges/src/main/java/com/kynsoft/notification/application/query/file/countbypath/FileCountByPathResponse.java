package com.kynsoft.notification.application.query.file.countbypath;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.notification.domain.dto.FilePathCountDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class FileCountByPathResponse implements IResponse {
    private UUID businessId;
    private List<FilePathCountDto> pathCounts;
    private Long totalCount;
    private Double totalSizeGB;
    
    public FileCountByPathResponse(UUID businessId, List<FilePathCountDto> pathCounts, Long totalCount) {
        this.businessId = businessId;
        this.pathCounts = pathCounts;
        this.totalCount = totalCount;
    }
    
    public FileCountByPathResponse(UUID businessId, List<FilePathCountDto> pathCounts, Long totalCount, Double totalSizeGB) {
        this.businessId = businessId;
        this.pathCounts = pathCounts;
        this.totalCount = totalCount;
        this.totalSizeGB = totalSizeGB;
    }
}