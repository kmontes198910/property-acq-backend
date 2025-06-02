# Microservicio wa-messaging

Este microservicio gestiona el envío de mensajes de WhatsApp a través de la WhatsApp Business API.

## Arquitectura actual

Se ha implementado un sistema de procesamiento asíncrono con colas internas que reemplaza la dependencia previa de Kafka. Las principales ventajas son:

1. **Menor complejidad operativa**: No requiere un clúster de Kafka externo
2. **Menor latencia**: Las solicitudes se procesan directamente en memoria
3. **Mayor sencillez**: Reduce las dependencias externas del sistema

## Componentes principales

- **MessageQueueConfig**: Define dos colas internas usando `BlockingQueue`:
  - `messageSendQueue`: Para solicitudes de envío de mensajes
  - `messageStatusQueue`: Para notificaciones de estado de mensajes

- **MessageQueueConsumerService**: Procesa las solicitudes de las colas internas
  - Utiliza programación asíncrona con Spring `@Scheduled`
  - Reemplaza los KafkaListeners usados anteriormente

- **IntegrationController**: Expone endpoints REST para la integración con otros servicios
  - `/api/integration/messages`: Para encolar solicitudes de envío (reemplaza el topic `wa-message-send`)
  - `/api/integration/status`: Para actualizar estados (reemplaza el topic `wa-message-status`)

## Integración con otros servicios

Para integrar otros microservicios con este sistema:

### Envío de mensajes

```http
POST /api/integration/messages
Content-Type: application/json

{
  "recipientPhone": "573001234567",
  "recipientName": "Nombre del paciente",
  "messageContent": "Contenido del mensaje",
  "messageType": "TEXT"
}
```

### Actualización de estados

```http
POST /api/integration/status
Content-Type: application/json

{
  "externalId": "id-externo-del-mensaje",
  "status": "DELIVERED",
  "timestamp": "2023-05-15T14:30:00",
  "errorMessage": null
}
```

## Configuración

Propiedades principales en `application.properties`:

```properties
# Configuración de colas
wa.messaging.queue.poll-interval=100
wa.messaging.queue.batch-size=10
wa.messaging.retry.max-attempts=3
wa.messaging.retry.delay=60000
```

## Escalabilidad

La arquitectura soporta:
- Procesamiento concurrente con pool de hilos configurable
- Reintentos automáticos de mensajes fallidos
- Monitoreo de estado de mensajes
