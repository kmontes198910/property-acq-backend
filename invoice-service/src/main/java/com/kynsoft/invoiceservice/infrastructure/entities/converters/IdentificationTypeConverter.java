package com.kynsoft.invoiceservice.infrastructure.entities.converters;

import com.kynsoft.invoiceservice.infrastructure.entities.IdentificationType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class IdentificationTypeConverter implements AttributeConverter<IdentificationType, String> {

    @Override
    public String convertToDatabaseColumn(IdentificationType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public IdentificationType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return IdentificationType.fromCode(dbData);
    }
}