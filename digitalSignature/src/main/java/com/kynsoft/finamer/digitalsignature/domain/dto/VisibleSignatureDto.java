package com.kynsoft.finamer.digitalsignature.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisibleSignatureDto {
    private Integer page; // 1 = primera página
    private Float x;      // posición X en puntos
    private Float y;      // posición Y en puntos
    private Float width;  // ancho en puntos (opcional)
    private Float height; // alto en puntos (opcional)
}