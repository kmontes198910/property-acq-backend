package com.kynsoft.propertyacqcenter.domain.enums;

/**
 * Enumeración de tipos de empresas inmobiliarias
 */
public enum RealEstateCompanyType {
    BROKER("Broker", "BROKER", "Agente inmobiliario que actúa como intermediario entre compradores y vendedores", "Century 21, RE/MAX, Coldwell Banker"),
    DEVELOPER("Developer", "DEVELOPER", "Empresa dedicada a la construcción y desarrollo de proyectos inmobiliarios", "Lennar, DR Horton, Taylor Morrison"),
    INVESTOR("Investor", "INVESTOR", "Entidad dedicada a invertir en propiedades inmobiliarias", "Blackstone, Brookfield, Starwood Capital"),
    PROPERTY_MANAGER("Property Manager", "PROPMAN", "Compañía que gestiona propiedades para propietarios", "Greystar, Lincoln Property Company"),
    REIT("Real Estate Investment Trust", "REIT", "Fideicomiso de inversión inmobiliaria que cotiza en bolsa", "Equity Residential, Simon Property Group"),
    LENDER("Lender", "LENDER", "Entidad financiera especializada en préstamos inmobiliarios", "Quicken Loans, PennyMac"),
    TITLE_COMPANY("Title Company", "TITLE", "Empresa que verifica la titularidad de propiedades", "First American, Fidelity National"),
    ESCROW_COMPANY("Escrow Company", "ESCROW", "Compañía que gestiona el proceso de cierre de ventas", "Escrow of the West, North American Title"),
    HOLDING_COMPANY("Holding Company", "HOLDING", "Empresa que posee y gestiona propiedades a largo plazo", "Boston Properties, Prologis"),
    FLIPPER("Flipper", "FLIPPER", "Empresa que compra propiedades para renovar y vender rápidamente", "We Buy Houses, HomeVestors"),
    OTHER("Other", "OTHER", "Otros tipos de empresas relacionadas con bienes raíces", "");

    private final String name;
    private final String code;
    private final String description;
    private final String examples;

    RealEstateCompanyType(String name, String code, String description, String examples) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.examples = examples;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getExamples() {
        return examples;
    }

    /**
     * Obtiene un tipo de compañía a partir de su código
     * @param code Código a buscar
     * @return El tipo de compañía correspondiente o OTHER si no se encuentra
     */
    public static RealEstateCompanyType fromCode(String code) {
        for (RealEstateCompanyType type : values()) {
            if (type.getCode().equalsIgnoreCase(code)) {
                return type;
            }
        }
        return OTHER;
    }
}