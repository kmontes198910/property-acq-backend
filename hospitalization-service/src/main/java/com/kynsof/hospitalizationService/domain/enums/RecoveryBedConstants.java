package com.kynsof.hospitalizationService.domain.enums;


public class RecoveryBedConstants {

    // Estados de camas de recuperación
    public static final String BED_STATUS_AVAILABLE = "AVAILABLE";
    public static final String BED_STATUS_OCCUPIED = "OCCUPIED";
    public static final String BED_STATUS_RESERVED = "RESERVED";
    public static final String BED_STATUS_MAINTENANCE = "MAINTENANCE";
    public static final String BED_STATUS_OUT_OF_SERVICE = "OUT_OF_SERVICE";

    // Tipos de camas de recuperación
    public static final String BED_TYPE_STANDARD = "STANDARD";
    public static final String BED_TYPE_ICU = "ICU";
    public static final String BED_TYPE_PEDIATRIC = "PEDIATRIC";
    public static final String BED_TYPE_SPECIAL_CARE = "SPECIAL_CARE";

    // Estados de asignaciones de camas
    public static final String ASSIGNMENT_STATUS_ASSIGNED = "ASSIGNED";
    public static final String ASSIGNMENT_STATUS_IN_USE = "IN_USE";
    public static final String ASSIGNMENT_STATUS_COMPLETED = "COMPLETED";
    public static final String ASSIGNMENT_STATUS_CANCELLED = "CANCELLED";

    private RecoveryBedConstants() {
        // Constructor privado para evitar instanciación
    }
}