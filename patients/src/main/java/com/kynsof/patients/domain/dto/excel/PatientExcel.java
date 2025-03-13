package com.kynsof.patients.domain.dto.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PatientExcel {
    @ExcelProperty("Identificador")
    private String identification;
    @ExcelProperty("Nombre")
    private String name;
    @ExcelProperty("Apellidos")
    private String lastName;
    @ExcelProperty("Genero")
    private String gender;
    @ExcelProperty("Correo")
    private String email;
    @ExcelProperty("Telefono")
    private String telephone;
    @ExcelProperty("Fecha Nacimiento")
    private String birthdayDate;

    private int rowIndex;
}
