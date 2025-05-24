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