package com.kynsoft.notification.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.notification.application.query.file.countbypath.FileCountByPathResponse;
import com.kynsoft.notification.domain.dto.AFileDto;
import com.kynsoft.notification.domain.dto.FilePathCountDto;
import com.kynsoft.notification.domain.dto.FileMimeTypeCountDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IAFileService {
    UUID create(AFileDto object);
    void update(AFileDto object);
    void delete(AFileDto object);
    AFileDto findById(UUID id);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
    AFileDto findByUrl(String url);
    FileCountByPathResponse countByPath(UUID businessId);
    List<FilePathCountDto> getFilePathCount(UUID businessId);
    Double calculateTotalDiskSpaceInGB(UUID businessId);
    Double calculateTotalDiskSpaceInMB(UUID businessId);
    List<FileMimeTypeCountDto> getFileMimeTypeCount(UUID businessId);
}
