package com.kynsoft.propertyacqcenter.domain.enums;

/**
 * Categorías para los contactos en Exofarm
 */
public enum ContactCategory {
    CLIENTE("Cliente"),
    PROVEEDOR("Proveedor"),
    DISTRIBUIDOR("Distribuidor"),
    EMPLEADO("Empleado"),
    SOCIO_ESTRATEGICO("Socio Estratégico"),
    CONSULTOR("Consultor"),
    GOBIERNO("Gobierno"),
    OTRO("Otro");

    private final String label;

    ContactCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    
    /**
     * Obtiene una categoría a partir de su etiqueta
     * @param label La etiqueta a buscar
     * @return La categoría correspondiente o OTRO si no se encuentra
     */
    public static ContactCategory fromLabel(String label) {
        for (ContactCategory category : values()) {
            if (category.getLabel().equalsIgnoreCase(label)) {
                return category;
            }
        }
        return OTRO;
    }
}