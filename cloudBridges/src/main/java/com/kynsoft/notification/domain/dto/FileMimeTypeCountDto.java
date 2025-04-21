package com.kynsoft.notification.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileMimeTypeCountDto {
    private String mimeType;
    private Long count;
    private Double totalSizeMb;
}