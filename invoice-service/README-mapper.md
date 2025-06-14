# Patrón de Diseño Mapper en la Aplicación de Facturación

## Descripción

Este documento explica la implementación del patrón de diseño Mapper para separar la lógica de transformación de datos de las clases de negocio y mejorar la mantenibilidad del código.

## Motivación

La aplicación necesita convertir entidades del modelo de negocio (como `Invoice`) a modelos específicos utilizados por librerías externas (como `Factura` del SRI). Esta transformación incluía lógica compleja dentro de la clase `DraftInvoiceJob`, lo que generaba varios problemas:

1. La clase `DraftInvoiceJob` tenía demasiadas responsabilidades (violando el principio de responsabilidad única)
2. La lógica de transformación estaba acoplada a la lógica del job
3. Era difícil probar la transformación de manera aislada
4. La reutilización del código de transformación era complicada

## Solución Implementada

Se ha creado la clase `MapperInvoice` que implementa el patrón de diseño Mapper para separar la lógica de transformación de datos.

### Beneficios

1. **Separación de responsabilidades**: La clase `MapperInvoice` se encarga únicamente de la conversión de datos.
2. **Facilidad de pruebas**: Es posible probar la lógica de transformación de manera independiente.
3. **Reutilización de código**: Cualquier componente que necesite convertir objetos `Invoice` a `Factura` puede usar este mapper.
4. **Mantenibilidad mejorada**: Las modificaciones en la lógica de transformación no afectan la lógica de negocio.

## Estructura del Código

- `MapperInvoice`: Clase principal que implementa la conversión de objetos `Invoice` a `ProcessInvoice`.
- `ProcessInvoice`: Clase que encapsula una factura generada junto con su certificado digital.

## Ejemplo de Uso

```java
@Service
public class SomeService {
    private final MapperInvoice mapperInvoice;
    
    public SomeService(MapperInvoice mapperInvoice) {
        this.mapperInvoice = mapperInvoice;
    }
    
    public void processInvoice(Invoice invoice) {
        // Convertir la entidad Invoice a ProcessInvoice utilizando el mapper
        ProcessInvoice processInvoice = mapperInvoice.convertToFactura(invoice);
        
        // Usar el objeto ProcessInvoice
        // ...
    }
}
```

## Consideraciones

- El mapper depende de servicios como `IInvoiceIssuerService` y `CredentialUtil` para acceder a información adicional durante la transformación.
- La lógica de conversión incluye el manejo de certificados digitales y contraseñas encriptadas.

## Referencias

- [Patrón de diseño Mapper](https://martinfowler.com/eaaCatalog/dataMapper.html)
- [Principio de Responsabilidad Única (SRP)](https://en.wikipedia.org/wiki/Single-responsibility_principle)
