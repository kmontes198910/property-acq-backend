package com.kynsof.treatments.domain.service;


import java.util.UUID;

public interface IBusinessBalanceService {

    /**
     * Realiza un descuento en el balance del negocio.
     *
     * @param businessId Identificador único del negocio.
     * @param balance    Monto a descontar.
     * @return Respuesta del servicio como cadena de texto.
     */
    String discountBusinessBalance(UUID businessId, double balance);
}