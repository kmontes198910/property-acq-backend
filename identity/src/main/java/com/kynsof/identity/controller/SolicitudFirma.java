package com.kynsof.identity.controller;

import lombok.Data;

@Data
public class SolicitudFirma {
    private String rutaP12;
    private String password;
    private String rutaPdfEntrada;
    private String rutaPdfSalida;
    private RectanguloFirma rectangulo;
}

