package com.kynsof.calendar.application.command.receipt.create;

import com.kynsof.calendar.domain.dto.PatientDto;
import com.kynsof.calendar.domain.dto.ReceiptDto;
import com.kynsof.calendar.domain.dto.ScheduleDto;
import com.kynsof.calendar.domain.dto.ServiceDto;
import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import com.kynsof.calendar.domain.dto.enumType.PatientStatus;
import com.kynsof.calendar.domain.service.IPatientsService;
import com.kynsof.calendar.domain.service.IReceiptService;
import com.kynsof.calendar.domain.service.IScheduleService;
import com.kynsof.calendar.domain.service.IServiceService;
import com.kynsof.calendar.infrastructure.service.http.PatientHttpUUIDService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.http.entity.PatientHttp;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class CreateReceiptCommandHandler implements ICommandHandler<CreateReceiptCommand> {

    private final IReceiptService service;
    private final IPatientsService servicePatient;
    private final IScheduleService serviceSchedule;
    private final IServiceService serviceService;
    private final RedissonClient redissonClient;
    private final PatientHttpUUIDService patientHttpUUIDService;

    public CreateReceiptCommandHandler(IReceiptService service, IPatientsService servicePatient,
                                       IScheduleService serviceSchedule, IServiceService serviceService,
                                       RedissonClient redissonClient,
                                       PatientHttpUUIDService patientHttpUUIDService) {
        this.service = service;
        this.servicePatient = servicePatient;
        this.serviceSchedule = serviceSchedule;
        this.serviceService = serviceService;
        this.redissonClient = redissonClient;
        this.patientHttpUUIDService = patientHttpUUIDService;
    }

    @Override
    public void handle(CreateReceiptCommand command) {
        // Verificar si el paciente, el horario y el servicio existen
        PatientDto _patient =  this.servicePatient.findById(command.getUser());
        ScheduleDto _schedule = this.serviceSchedule.findById(command.getSchedule());
        ServiceDto _service = this.serviceService.findByIds(command.getService());

        // Configurar el bloqueo para el stock del horario
        String lockKey = "schedule_stock_lock:" + _schedule.getId();
        RLock lock = redissonClient.getLock(lockKey);

        try {
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                // Verificar si hay stock disponible
                if (_schedule.getStock() == 0) {
                    throw new BusinessException(DomainErrorMessage.SCHEDULE_IS_NOT_AVAIBLE, "La cita no está disponible.");
                }

                // Reducir el stock del horario
                _schedule.setStock(_schedule.getStock() - 1);
//                if (_schedule.getStock() == 0) {
//                    _schedule.setStatus(EStatusSchedule.SOLD_OUT);
//                }
                this.serviceSchedule.update(_schedule); // Actualiza el estado del horario en la base de datos

                // Crear el recibo
                ReceiptDto receiptDto = new ReceiptDto(
                        UUID.randomUUID(),
                        command.getPrice() == null ? _service.getNormalAppointmentPrice() : command.getPrice(),
                        command.getExpress(),
                        command.getReasons(),
                        _patient,
                        _schedule,
                        _service,
                        command.getStatus() == null ? EStatusReceipt.PENDING : command.getStatus()
                );

                receiptDto.setIpAddressCreate(command.getIpAddress());
                receiptDto.setUserAgentCreate(command.getUserAgent());

                // Crear el recibo en la base de datos
                UUID id = service.create(receiptDto);
                command.setId(id);
            } else {
                throw new BusinessException(DomainErrorMessage.SCHEDULE_IS_NOT_AVAIBLE, "El horario está siendo procesado.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BusinessException(DomainErrorMessage.SCHEDULE_IS_NOT_AVAIBLE, "La cita no está disponible.");
        } finally {
            lock.unlock(); // Liberar el bloqueo
        }
    }
}