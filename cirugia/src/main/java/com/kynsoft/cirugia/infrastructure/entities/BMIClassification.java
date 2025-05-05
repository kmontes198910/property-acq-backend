package com.kynsoft.cirugia.infrastructure.entities;

import lombok.Getter;

public enum BMIClassification {
    UNDERWEIGHT("Underweight", 0, 18.5),
    NORMAL_WEIGHT("Normal weight", 18.5, 24.9),
    OVERWEIGHT("Overweight", 25.0, 29.9),
    OBESITY_GRADE_1("Obesity grade 1", 30.0, 34.9),
    OBESITY_GRADE_2("Obesity grade 2", 35.0, 39.9),
    OBESITY_GRADE_3("Obesity grade 3 (morbid)", 40.0, Double.MAX_VALUE);

    @Getter
    private final String description;
    private final double min;
    private final double max;

    BMIClassification(String description, double min, double max) {
        this.description = description;
        this.min = min;
        this.max = max;
    }

    public static BMIClassification classify(double bmi) {
        for (BMIClassification category : BMIClassification.values()) {
            if (bmi >= category.min && bmi < category.max) {
                return category;
            }
        }
        return null; // En teoría nunca debería ocurrir
    }
}
