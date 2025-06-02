package com.kynsoft.notification.application.query.file.countbymimetype;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.notification.domain.dto.FileMimeTypeCountDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class FileCountByMimeTypeResponse implements IResponse {
    private UUID businessId;
    private List<FileMimeTypeCountDto> mimeTypeStats;
    private Long totalFileCount;
    private Double totalSizeGB;


    public FileCountByMimeTypeResponse(UUID businessId, List<FileMimeTypeCountDto> mimeTypeStats, 
                                      Long totalFileCount, Double totalSizeGB) {
        this.businessId = businessId;
        this.mimeTypeStats = mimeTypeStats;
        this.totalFileCount = totalFileCount;
        this.totalSizeGB = totalSizeGB;
    }
}