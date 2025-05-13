package com.kynsoft.cirugia.application.command.bedassignment.create;

import lombok.Getter;

@Getter
public enum SurgeryStage {
    PRE_OPERATIVO("preOperativo"),
    POST_OPERATIVO("postOperativo"),
    HOSPITALIZACION("hospitalizacion");

    private final String value;

    SurgeryStage(String value) {
        this.value = value;
    }

    public static SurgeryStage fromValue(String value) {
        for (SurgeryStage stage : SurgeryStage.values()) {
            if (stage.value.equalsIgnoreCase(value)) {
                return stage;
            }
        }
        throw new IllegalArgumentException("Etapa de cirugía no válida: " + value);
    }
}
