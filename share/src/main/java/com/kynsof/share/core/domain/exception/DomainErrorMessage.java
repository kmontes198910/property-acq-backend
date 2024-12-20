package com.kynsof.share.core.domain.exception;

import jakarta.annotation.Nullable;

public enum DomainErrorMessage implements IDomainErrorMessage {
    PATIENTS_NOT_FOUND(601, Series.DOMAIN_ERROR, "Pacientes no encontrados."),
    QUALIFICATION_NOT_FOUND(602, Series.DOMAIN_ERROR, "Calificación no encontrada."),
    QUALIFICATION_OR_ID_NULL(603, Series.DOMAIN_ERROR, "La calificación o el ID no pueden ser nulos."),
    BUSINESS_NOT_FOUND(604, Series.DOMAIN_ERROR, "Negocio no encontrado."),
    BUSINESS_OR_ID_NULL(605, Series.DOMAIN_ERROR, "El negocio o el ID no pueden ser nulos."),
    EXISTS_SCHEDULE_SOME_DATE_WHOSE_TIME_RANGE(606, Series.DOMAIN_ERROR, "Existe un horario en la misma fecha cuyo rango de tiempo coincide en algún momento con lo que deseas crear."),
    EXISTS_SCHEDULE_WITH_DATE_STARTTIME_ENDTIME(607, Series.DOMAIN_ERROR, "Existe un horario con la misma fecha, hora de inicio y hora de fin."),
    SCHEDULE_NOT_FOUND(608, Series.DOMAIN_ERROR, "Horario no encontrado."),
    SCHEDULE_CANNOT_BE_EQUALS_STARTTIME_ENDTIME(609, Series.DOMAIN_ERROR, "La hora de inicio y la hora de fin no pueden ser iguales."),
    SCHEDULE_DATE_LESS_THAN_CURRENT_DATE(610, Series.DOMAIN_ERROR, "La fecha proporcionada es menor que la fecha actual."),
    SCHEDULE_INITIAL_TIME_IS_PASSED(611, Series.DOMAIN_ERROR, "El tiempo inicial ha pasado."),
    SCHEDULE_END_TIME_IS_LESS_THAN(612, Series.DOMAIN_ERROR, "La hora de finalización proporcionada es menor que la hora de inicio."),
    SCHEDULE_EXISTS_SOME_TIME_STARTTIME_EDNTIME(613, Series.DOMAIN_ERROR, "Existe un horario con la misma fecha, hora de inicio y hora de fin."),
    RESOURCE_NOT_FOUND(614, Series.DOMAIN_ERROR, "Recurso no encontrado."),
    RECEIPT_NOT_FOUND(615, Series.DOMAIN_ERROR, "Recibo no encontrado."),
    STATUS_NOT_ACCEPTED(616, Series.DOMAIN_ERROR, "Estado no aceptado, la cita fue atendida."),
    SCHEDULE_IS_NOT_AVAIBLE(617, Series.DOMAIN_ERROR, "El horario seleccionado no está disponible."),
    COLUMN_UNIQUE(618, Series.DOMAIN_ERROR, "El valor de la clave duplicada viola la restricción única."),
    QUALIFICATION_DESCRIPTION_NOT_NULL(619, Series.DOMAIN_ERROR, "¡La descripción de la calificación no puede ser nula!"),
    QUALIFICATION_DESCRIPTION_UNIQUE(620, Series.DOMAIN_ERROR, "¡La descripción de la calificación debe ser única!"),
    PERMISSION_NOT_FOUND(621, Series.DOMAIN_ERROR, "Permiso no encontrado."),
    PERMISSION_OR_ID_NULL(622, Series.DOMAIN_ERROR, "El permiso o el ID no pueden ser nulos."),
    ROLE_PERMISSION_NOT_FOUND(623, Series.DOMAIN_ERROR, "Permiso de rol no encontrado."),
    RELATIONSHIP_MUST_BE_UNIQUE(624, Series.DOMAIN_ERROR, "La relación ya existe."),
    OBJECT_NOT_NULL(625, Series.DOMAIN_ERROR, "El objeto no puede ser nulo."),
    USER_ROLE_BUSINESS_NOT_FOUND(626, Series.DOMAIN_ERROR, "Rol de usuario-negocio no encontrado."),
    ROLE_NOT_FOUND(627, Series.DOMAIN_ERROR, "Rol no encontrado."),
    ROLE_EXIT(628, Series.DOMAIN_ERROR, "Rol no encontrado."),
    BUSINESS_MODULE_NOT_FOUND(629, Series.DOMAIN_ERROR, "Módulo de negocio no encontrado."),
    MODULE_PERMISSION_NOT_FOUND(630, Series.DOMAIN_ERROR, "Permiso del módulo no encontrado."),
    BUSINESS_RUC(631, Series.DOMAIN_ERROR, "El RUC del negocio debe tener trece caracteres."),
    BUSINESS_RUC_MUST_BY_UNIQUE(632, Series.DOMAIN_ERROR, "El RUC del negocio debe ser único."),
    BUSINESS_NAME_MUST_BY_UNIQUE(633, Series.DOMAIN_ERROR, "El nombre del negocio debe ser único."),
    SCHEDULED_TASK_ALREADY_EXISTS(634, Series.DOMAIN_ERROR, "Ya existe una tarea programada para este servicio."),
    MODULE_NAME_CANNOT_BE_EMPTY(635, Series.DOMAIN_ERROR, "El nombre del módulo no puede estar vacío."),
    MODULE_DESCRIPTION_CANNOT_BE_EMPTY(636, Series.DOMAIN_ERROR, "La descripción del módulo no puede estar vacía."),
    MODULE_NAME_MUST_BY_UNIQUE(637, Series.DOMAIN_ERROR, "El nombre del módulo debe ser único."),
    MODULE_NOT_FOUND(638, Series.DOMAIN_ERROR, "Módulo no encontrado."),
    GEOGRAPHIC_LOCATION_NOT_FOUND(639, Series.DOMAIN_ERROR, "Ubicación geográfica no encontrada."),
    USER_NOT_FOUND(640, Series.DOMAIN_ERROR, "Usuario no encontrado."),
    USER_PERMISSION_BUSINESS_NOT_FOUND(641, Series.DOMAIN_ERROR, "Permiso de usuario-negocio no encontrado."),
    PERMISSION_CODE_MUST_BY_UNIQUE(642, Series.DOMAIN_ERROR, "El código del permiso debe ser único."),
    PERMISSION_CODE_CANNOT_BE_EMPTY(643, Series.DOMAIN_ERROR, "El código del permiso no puede estar vacío."),
    DEVICE_NOT_FOUND(644, Series.DOMAIN_ERROR, "Dispositivo no encontrado."),
    DEVICE_IP_VALIDATE(645, Series.DOMAIN_ERROR, "La dirección IP no es correcta."),
    DEVICE_SERIAL_CANNOT_BE_EMPTY(646, Series.DOMAIN_ERROR, "El serial del dispositivo no puede estar vacío."),
    DEVICE_EMAIL_VALIDATE(647, Series.DOMAIN_ERROR, "La dirección de correo es incorrecta."),
    CUSTOMER_NOT_FOUND(648, Series.DOMAIN_ERROR, "Cliente no encontrado."),
    PATIENT_IDENTIFICATION_MUST_BY_UNIQUE(649, Series.DOMAIN_ERROR, "La identificación del paciente debe ser única."),
    SERVICE_TYPE_NAME_MUST_BY_UNIQUE(650, Series.DOMAIN_ERROR, "El nombre del tipo de servicio debe ser único."),
    SERVICE_NAME_MUST_BY_UNIQUE(651, Series.DOMAIN_ERROR, "El nombre del servicio debe ser único."),
    VACCINE_MUST_BY_UNIQUE(652, Series.DOMAIN_ERROR, "El nombre de la vacuna debe ser único."),
    PROCEDURE_NOT_FOUND(653, Series.DOMAIN_ERROR, "Procedimiento no encontrado."),
    PROCEDURE_CODE_MUST_BY_UNIQUE(654, Series.DOMAIN_ERROR, "El código del procedimiento debe ser único."),
    TREATMENT_NOT_FOUND(654, Series.DOMAIN_ERROR, "Tratamiento no encontrado."),
    DIAGNOSIS_NOT_FOUND(655, Series.DOMAIN_ERROR, "Diagnóstico no encontrado."),
    EXAM_NOT_FOUND(656, Series.DOMAIN_ERROR, "Examen no encontrado."),
    INSURANCE_NOT_FOUND(657, Series.DOMAIN_ERROR, "Seguro no encontrado."),
    SERVICE_TYPE_NOT_FOUND(658, Series.DOMAIN_ERROR, "Tipo de servicio no encontrado."),
    SERVICE_NOT_FOUND(659, Series.DOMAIN_ERROR, "Servicio no encontrado."),
    SCHEDULED_DATE_IS_NOT_PRESENT(660, Series.DOMAIN_ERROR, "La fecha debe estar presente."),
    CONTACT_INFO_NOT_FOUND(661, Series.DOMAIN_ERROR, "Información de contacto no encontrada."),
    MEDICAL_INFO_NOT_FOUND(662, Series.DOMAIN_ERROR, "Información médica no encontrada."),
    MEDICINES_NOT_FOUND(663, Series.DOMAIN_ERROR, "Medicinas no encontradas."),
    EXAM_ORDER_NOT_FOUND(664, Series.DOMAIN_ERROR, "Orden de examen no encontrada."),
    VACCINE_NOT_FOUND(665, Series.DOMAIN_ERROR, "Vacuna no encontrada."),
    PATIENT_VACCINE_NOT_FOUND(666, Series.DOMAIN_ERROR, "Relación no encontrada."),
    EXTERNAL_CONSULTATION_NOT_FOUND(667, Series.DOMAIN_ERROR, "Consulta externa no encontrada."),
    DOCTOR_NOT_FOUND(668, Series.DOMAIN_ERROR, "Doctor no encontrado."),
    CIE10_NOT_FOUND(669, Series.DOMAIN_ERROR, "CIE10 no encontrado."),
    NOT_DELETE(1000, Series.DOMAIN_ERROR, "El elemento no se puede eliminar porque tiene un elemento relacionado."),
    MUST_BY_UNIQUE(1002, Series.DOMAIN_ERROR, "Debe ser único."),
    OBJECT_NOT_FOUNT(1003, Series.DOMAIN_ERROR, "Objeto no encontrado."),
    UUID_NOT_FORMAT(1004, Series.DOMAIN_ERROR, "Formato de UUID incorrecto."),
    STATUS_NOT_FORMAT(1005, Series.DOMAIN_ERROR, "Estado no aceptado."),
    ITEM_ALREADY_EXITS(1006, Series.DOMAIN_ERROR, "Ya existe el elemento en el sistema."),
    DB_CONNECTION_NOT_FOUND(1007, Series.DOMAIN_ERROR, "Conexión no encontrada."),
    PAYMENT_NOT_FOUND(1009, Series.DOMAIN_ERROR, "No se pudo procesar el pago."),
    PASSWORD_MISMATCH(1008, Series.DOMAIN_ERROR, "La contraseña no coincide."),
    PLACE_CODE_MUST_BY_UNIQUE(1009, Series.DOMAIN_ERROR, "El código del lugar debe ser único."),
    PLACE_NAME_MUST_BY_UNIQUE(1010, Series.DOMAIN_ERROR, "El nombre del lugar debe ser único."),
    BLOCK_CODE_MUST_BY_UNIQUE(1011, Series.DOMAIN_ERROR, "El código del bloque debe ser único."),
    BLOCK_NAME_MUST_BY_UNIQUE(1012, Series.DOMAIN_ERROR, "El nombre del bloque debe ser único."),
    PROCEDURE_NAME_MUST_BY_UNIQUE(1013, Series.DOMAIN_ERROR, "El nombre del procedimiento debe ser único."),
    MEDICINES_NAME_MUST_BY_UNIQUE(1014, Series.DOMAIN_ERROR, "El nombre del medicamento debe ser único."),
    SERVICE_TYPE_CODE_MUST_BY_UNIQUE(1015, Series.DOMAIN_ERROR, "El código del tipo de servicio debe ser único."),
    SERVICE_CODE_MUST_BY_UNIQUE(1016, Series.DOMAIN_ERROR, "El código del servicio debe ser único."),
    CONSULT_EXTERN_DATE_EXP(1017, Series.DOMAIN_ERROR, "La consulta externa no se puede modificar. La fecha de creación ya pasó..");
    private static final DomainErrorMessage[] VALUES;

    static {
        VALUES = values();
    }

    private final int value;

    private final Series series;

    private final String reasonPhrase;

    DomainErrorMessage(int value, Series series, String reasonPhrase) {
        this.value = value;
        this.series = series;
        this.reasonPhrase = reasonPhrase;
    }

    @Override
    public int value() {
        return this.value;
    }

    /**
     * Return the status series of this status code.
     */
    public Series series() {
        return this.series;
    }

    /**
     * Return the reason phrase of this status code.
     */
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    /**
     * Return a string representation of this status code.
     */
    @Override
    public String toString() {
        return this.value + " " + name();
    }

    /**
     * Return the {@code ApiErrorStatus} enum constant with the specified
     * numeric value.
     *
     * @param statusCode the numeric value of the enum to be returned
     * @return the enum constant with the specified numeric value
     * @throws IllegalArgumentException if this enum has no constant for the
     * specified numeric value
     */
    public static DomainErrorMessage valueOf(int statusCode) {
        DomainErrorMessage status = resolve(statusCode);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
        }
        return status;
    }

    /**
     * Resolve the given status code to an {@code ApiErrorStatus}, if possible.
     *
     * @param statusCode the ApiError status code (potentially non-standard)
     * @return the corresponding {@code ApiErrorStatus}, or {@code null} if not
     * found
     */
    @Nullable
    public static DomainErrorMessage resolve(int statusCode) {
        // Use cached VALUES instead of values() to prevent array allocation.
        for (DomainErrorMessage status : VALUES) {
            if (status.value == statusCode) {
                return status;
            }
        }
        return null;
    }

    /**
     * Enumeration of ApiError status series.
     * <p>
     * Retrievable via {@link DomainErrorMessage#series()}.
     */
    public enum Series {
        DOMAIN_ERROR
    }

}
