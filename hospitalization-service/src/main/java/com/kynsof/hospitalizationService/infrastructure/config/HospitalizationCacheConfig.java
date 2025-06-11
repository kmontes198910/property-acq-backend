package com.kynsof.hospitalizationService.infrastructure.config;

public class HospitalizationCacheConfig {
    public static final String SURGERY_CACHE = "surgery_cache";
    public static final String RECOVERY_BED_CACHE = "recovery_beds";
    public static final String BED_ASSIGNMENT_CACHE = "bed_assignments";
    public static final String SURGERY_SERVICE_CACHE = "surgery_service_cache";
    public static final String DIAGNOSIS_CACHE = "diagnosis_cache";
    public static final String TEAM_MEDICAL_CACHE = "team_medical_cache";
    public static final String VITAL_SIGNS_CACHE = "vital_signs";

    public static final String SERVICE_TYPE_CACHE = "service-type-cache";
    public static final String SCHEDULE_AVAILABILITY_CACHE = "schedule-availability-cache";
    public static final String BUSINESS_SERVICE_CACHE = "business-service-cache";
    public static final String RESOURCE_SERVICE_CACHE = "resource-service-cache";
    public static final String DASHBOARD_STATS_CACHE = "dashboard-stats-cache";
    public static final String BUSINESS_RESOURCE_CACHE = "business-resource-cache";
    public static final String SERVICE_CACHE = "service-cache";

    private HospitalizationCacheConfig() {
        // Constructor privado para evitar instanciación
    }
}