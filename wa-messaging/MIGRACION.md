# Guía de Migración desde Kafka

Este documento describe cómo migrar la integración con el servicio `wa-messaging` desde Kafka a la nueva arquitectura basada en REST.

## Cambios realizados

1. Se ha eliminado la dependencia de Kafka del servicio wa-messaging
2. Se han reemplazado los topics de Kafka por colas internas en memoria
3. Se ha implementado una API REST para la integración con otros servicios
4. Se ha creado un cliente Java para facilitar la integración

## Pasos para migrar (para otros microservicios)

### 1. Eliminar código de producción a Kafka

Buscar y eliminar código similar a:

```java
// Envío de mensajes a Kafka
kafkaTemplate.send("wa-message-send", sendMessageRequest);

// Envío de notificaciones de estado a Kafka
kafkaTemplate.send("wa-message-status", statusNotification);
```

### 2. Implementar integración via REST

#### Opción 1: Usar el cliente proporcionado

1. Copiar el archivo `WhatsAppMessagingClient.java` a tu microservicio
2. Asegurarse de tener `RestTemplate` configurado
3. Configurar la URL del servicio con la propiedad: `wa.messaging.service.url`
4. Usar el cliente para enviar mensajes:

```java
@Autowired
private WhatsAppMessagingClient messagingClient;

public void enviarRecordatorio() {
    SendMessageRequest request = SendMessageRequest.builder()
            .recipientPhone("573001234567")
            .recipientName("Nombre Paciente")
            .messageContent("Texto del mensaje")
            .messageType(MessageType.TEXT)
            .build();
            
    messagingClient.sendMessage(request);
}
```

#### Opción 2: Integración REST directa

```java
@Autowired
private RestTemplate restTemplate;

public void enviarMensaje() {
    SendMessageRequest request = // construir request
    
    restTemplate.postForEntity(
            "http://wa-messaging:8080/api/integration/messages",
            request,
            MessageResponse.class);
}
```

### 3. Actualizar configuración

Eliminar propiedades relacionadas con Kafka del archivo `application.properties`:

```properties
# Eliminar estas propiedades
spring.kafka.bootstrap-servers=...
spring.kafka.producer.*=...
spring.kafka.consumer.*=...
kafka.topic.*=...
```

## Ventajas de la nueva implementación

1. Menor complejidad operativa (no se requiere Kafka)
2. Comunicación más directa entre servicios
3. Mayor facilidad de pruebas
4. Respuesta inmediata sobre si el mensaje fue encolado correctamente

## Consideraciones de performance

La nueva implementación es adecuada para volúmenes de mensajes moderados. Si el volumen es muy alto (miles de mensajes por minuto), podría ser necesario escalar el servicio horizontalmente.

## Soporte

Para cualquier consulta sobre la migración, contactar al equipo de desarrollo.
