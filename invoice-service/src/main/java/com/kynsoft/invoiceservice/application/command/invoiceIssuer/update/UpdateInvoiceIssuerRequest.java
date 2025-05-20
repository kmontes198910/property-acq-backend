package com.kynsoft.invoiceservice.application.command.invoiceIssuer.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateInvoiceIssuerRequest {
    private String ruc;
    private String businessName;
    private String commercialName;
    private String establishment;
    private boolean pointOfSale;
    private String address;
    private String emissionPoint;
    private String email;
    private String phone;
    private String specialTaxpayer;
    private String retentionAgent;
    private String rimpeRegime;
    private String logoUrl;
    /**
     * Ambiente del emisor. Se espera un solo carácter.
     * Ejemplos de valores válidos:
     * - "1": Ambiente de pruebas
     * - "2": Ambiente de producción
     * 
     * Nota: Si se proporciona un valor con más de un carácter,
     * solo se utilizará el primero.
     */
    private String environment;
    private String website;
    /**
     * Indica si el emisor está obligado a llevar contabilidad
     */
    private Boolean accountingObligated;
    /**
     * Indica si el emisor está bajo régimen de microempresas
     */
    private Boolean microenterprisesRegime;
    /**
     * Indica si se deben enviar correos electrónicos
     */
    private Boolean sendEmails;
    private Boolean status;
    private String digitalCertP12;
    private String digitalCertPassword;
    
    /**
     * Obtiene el valor del punto de venta
     * 
     * @return Verdadero si es un punto de venta, falso de lo contrario
     */
    public boolean getPointOfSale() {
        return pointOfSale;
    }
}