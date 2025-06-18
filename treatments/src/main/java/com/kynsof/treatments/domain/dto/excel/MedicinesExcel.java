package com.kynsof.treatments.domain.dto.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MedicinesExcel {
    @ExcelProperty("PRODUCTO")
    private String product;
    @ExcelProperty("LABORATORIO")
    private String lab;
    @ExcelProperty("CLASIFICACION")
    private String clasification;
    @ExcelProperty("LINEA")
    private String line;
    @ExcelProperty("MARCA")
    private String brands;
    @ExcelProperty("IDBARRA")
    private String barcode;
    @ExcelProperty("PRESENTACION")
    private String presentation;
    @ExcelProperty("MEDIDA")
    private String measure;
    @ExcelProperty("CONCENTRACION")
    private String concentration;
    @ExcelProperty("FRACCION")
    private String fraction;
    @ExcelProperty("GENERICO")
    private String generic;
    @ExcelProperty("CAT1")
    private String cat1;
    @ExcelProperty("CAT2")
    private String cat2;
    @ExcelProperty("CAT3")
    private String cat3;
    @ExcelProperty("CAT4")
    private String cat4;

    private int rowIndex;
}
