package com.kynsoft.notification.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilePathCountDto {
    private String path;
    private Long count;
    private Double totalSizeMb;
}