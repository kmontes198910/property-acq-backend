package com.kynsoft.cirugia.domain.util;

import lombok.Getter;

public class BMIClassification {

    @Getter
    public enum Classification {
        BAJO_PESO("Bajo peso"),
        NORMAL("Normal"),
        SOBREPESO("Sobrepeso"),
        OBESIDAD_GRADO_1("Obesidad grado 1"),
        OBESIDAD_GRADO_2("Obesidad grado 2"),
        OBESIDAD_GRADO_3("Obesidad grado 3");

        private final String description;

        Classification(String description) {
            this.description = description;
        }
    }

    /**
     * Calcula el Índice de Masa Corporal (IMC) basado en el peso y la altura.
     * IMC = peso (kg) / (altura (m))^2
     *
     * @param weight peso en kilogramos
     * @param height altura en metros
     * @return valor del IMC como Double
     */
    public static Double calculateBMI(Double weight, Double height) {
        if (weight == null || height == null || height <= 0) {
            return null;
        }
        
        return weight / (height * height);
    }

    /**
     * Determina la clasificación del IMC según los estándares de la OMS.
     *
     * @param bmi Índice de Masa Corporal
     * @return la clasificación correspondiente
     */
    public static Classification getClassification(Double bmi) {
        if (bmi == null) {
            return null;
        }
        
        if (bmi < 18.5) {
            return Classification.BAJO_PESO;
        } else if (bmi < 25) {
            return Classification.NORMAL;
        } else if (bmi < 30) {
            return Classification.SOBREPESO;
        } else if (bmi < 35) {
            return Classification.OBESIDAD_GRADO_1;
        } else if (bmi < 40) {
            return Classification.OBESIDAD_GRADO_2;
        } else {
            return Classification.OBESIDAD_GRADO_3;
        }
    }
}