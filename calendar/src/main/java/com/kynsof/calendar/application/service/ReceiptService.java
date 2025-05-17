package com.kynsof.calendar.application.service;

import com.kynsof.calendar.application.query.ReceiptResponse;
import com.kynsof.calendar.domain.dto.ReceiptDto;
import com.kynsof.calendar.domain.dto.ReceiptSummaryDTO;
import com.kynsof.calendar.domain.dto.ScheduleDto;
import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import com.kynsof.calendar.domain.dto.enumType.EStatusSchedule;
import com.kynsof.calendar.domain.service.IReceiptService;
import com.kynsof.calendar.infrastructure.entity.Receipt;
import com.kynsof.calendar.infrastructure.entity.Resource;
import com.kynsof.calendar.infrastructure.entity.Schedule;
import com.kynsof.calendar.infrastructure.repository.command.ReceiptWriteDataJPARepository;
import com.kynsof.calendar.infrastructure.repository.command.ScheduleWriteDataJPARepository;
import com.kynsof.calendar.infrastructure.repository.query.ReceiptReadDataJPARepository;
import com.kynsof.calendar.infrastructure.repository.query.ResourceReadDataJPARepository;
import com.kynsof.calendar.infrastructure.repository.query.ScheduleReadDataJPARepository;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReceiptService implements IReceiptService {

    private final ReceiptReadDataJPARepository receiptRepositoryQuery;

    private final ReceiptWriteDataJPARepository receiptRepositoryCommand;
    private final ScheduleReadDataJPARepository scheduleRepositoryQuery;
    private final ScheduleWriteDataJPARepository scheduleRepositoryCommand;
    private final ResourceReadDataJPARepository resourceRepositoryQuery;

    private final ScheduleServiceImpl scheduleServiceImpl;

    public ReceiptService(ReceiptReadDataJPARepository receiptRepositoryQuery,
                          ReceiptWriteDataJPARepository receiptRepositoryCommand, ScheduleReadDataJPARepository scheduleRepositoryQuery, ScheduleWriteDataJPARepository scheduleRepositoryCommand, ResourceReadDataJPARepository resourceRepositoryQuery, ScheduleServiceImpl scheduleServiceImpl) {
        this.receiptRepositoryQuery = receiptRepositoryQuery;
        this.receiptRepositoryCommand = receiptRepositoryCommand;
        this.scheduleRepositoryQuery = scheduleRepositoryQuery;
        this.scheduleRepositoryCommand = scheduleRepositoryCommand;
        this.resourceRepositoryQuery = resourceRepositoryQuery;
        this.scheduleServiceImpl = scheduleServiceImpl;

    }


    @Override
    @Transactional
    public UUID create(ReceiptDto receipt) {

        if (receipt.getSchedule().getStatus() != EStatusSchedule.AVAILABLE) {
            throw new BusinessException(DomainErrorMessage.SCHEDULE_IS_NOT_AVAIBLE, "The selected schedule is not available.");
        }
//        var stock = receipt.getSchedule().getStock() - 1;
//        receipt.getSchedule().setStock(stock);
        if (receipt.getSchedule().getStock() == 0) {
            receipt.getSchedule().setStatus(EStatusSchedule.SOLD_OUT);
        }
        Receipt entity = this.receiptRepositoryCommand.save(new Receipt(receipt));
        return entity.getId();

    }

    @Override
    public void delete(UUID id) {
        ReceiptDto _receipt = this.findById(id);
        this.changeState(_receipt, EStatusReceipt.CANCEL);
    }

    @Override
    public ReceiptDto findById(UUID id) {
        Optional<Receipt> object = this.receiptRepositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }
        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.RESOURCE_NOT_FOUND, new ErrorField("id", "Resource not found.")));
    }

    @Override
    @Transactional
    public void update(ReceiptDto dto) {
        Receipt receipt = new Receipt(dto);
        this.receiptRepositoryCommand.save(receipt);
    }

    public void changeState(ReceiptDto receipt, EStatusReceipt status) {

        switch (status) {
            case CANCEL: {
                //TODO revisar la logica para poner agotada el scheduled
                if (!receipt.getStatus().equals(EStatusReceipt.ATTENDED)) {
                    //Liberando el schedule
                    ScheduleDto _schedule = receipt.getSchedule();
                    _schedule.setStatus(EStatusSchedule.AVAILABLE);
                    this.scheduleServiceImpl.create(_schedule);

                    receipt.setSchedule(null);

                    receipt.setStatus(status);
                    receiptRepositoryCommand.save(new Receipt(receipt));
                    //return new UpdateReceiptResponse("Appointment updated successfully.", true);
                } else {
                    throw new BusinessException(DomainErrorMessage.STATUS_NOT_ACCEPTED, "Status not accepted, the appointment was attended.");
                }
            }
            case CONFIRMED: {
                if (receipt.getStatus().equals(EStatusReceipt.PRE_RESERVE) || receipt.getStatus().equals(EStatusReceipt.CONFIRMED)) {
                    ScheduleDto _schedule = receipt.getSchedule();
                    // _schedule.setStatus(EStatusSchedule.RESERVED);
                    receipt.setSchedule(_schedule);

                    receipt.setStatus(status);
                    receiptRepositoryCommand.save(new Receipt(receipt));
                    //return new UpdateReceiptResponse("Appointment updated successfully.", true);
                } else {
                    throw new BusinessException(DomainErrorMessage.STATUS_NOT_ACCEPTED, "Status not accepted, the appointment was attended.");
                    //return new UpdateReceiptResponse("Status not accepted, appointment status: " + receipt.getStatus().name(), false);
                }
            }
            case ATTENDED: {
                ScheduleDto _schedule = receipt.getSchedule();
                // _schedule.setStatus(EStatusSchedule.ATTENDED);
                receipt.setSchedule(_schedule);

                receipt.setStatus(status);
                receiptRepositoryCommand.save(new Receipt(receipt));
            }
            default: {
                throw new BusinessException(DomainErrorMessage.STATUS_NOT_ACCEPTED, "Status not accepted.");
                //return new UpdateReceiptResponse("Status not accepted.", false);
            }
        }
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        for (FilterCriteria filter : filterCriteria) {
            if ("status".equals(filter.getKey()) && filter.getValue() instanceof String) {
                try {
                    EStatusReceipt enumValue = EStatusReceipt.valueOf((String) filter.getValue());
                    filter.setValue(enumValue);
                } catch (IllegalArgumentException e) {
                    System.err.println("Valor inválido para el tipo Enum Receipt: " + filter.getValue());
                }
            }
        }
        GenericSpecificationsBuilder<Receipt> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Receipt> data = this.receiptRepositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    @Override
    public Optional<Receipt> findByRequestId(String requestId) {
        return this.receiptRepositoryQuery.findByRequestId(requestId);
    }

    private PaginatedResponse getPaginatedResponse(Page<Receipt> data) {
        List<ReceiptResponse> patients = new ArrayList<>();
        for (Receipt o : data.getContent()) {
            patients.add(new ReceiptResponse(o.toAggregate()));
        }
        return new PaginatedResponse(patients, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public List<Map<String, Object>> getAppointmentsByStatus(UUID businessId) {
        List<Object[]> results = receiptRepositoryQuery.countAppointmentsByStatusForBusiness(businessId);

        // Map para almacenar los resultados con valores predeterminados
        Map<EStatusReceipt, Long> statusCounts = Arrays.stream(EStatusReceipt.values())
                .collect(Collectors.toMap(status -> status, status -> 0L));

        // Rellenar el mapa con los resultados de la consulta
        results.forEach(result -> {
            EStatusReceipt status = (EStatusReceipt) result[0];
            Long count = (Long) result[1];
            statusCounts.put(status, count);
        });

        // Convertir el mapa a una lista de mapas
        return statusCounts.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("status", entry.getKey().name());
                    map.put("count", entry.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void updateScheduled(UUID receiptId, UUID scheduledId) {
        Receipt receipt = receiptRepositoryCommand.findById(receiptId).orElse(null);
        assert receipt != null;
        Schedule scheduleOld = receipt.getSchedule();
        Schedule scheduleNew = this.scheduleRepositoryQuery.findById(scheduledId).orElse(null);
        assert scheduleNew != null;
        scheduleNew.setStock(scheduleNew.getStock() - 1);
        scheduleOld.setStock(scheduleOld.getStock() + 1);
        scheduleOld.setStatus(EStatusSchedule.AVAILABLE);
       if(scheduleNew.getStock()==0){
           scheduleNew.setStatus(EStatusSchedule.SOLD_OUT);
       }
       receipt.setSchedule(scheduleNew);
       receiptRepositoryCommand.save(receipt);
       scheduleRepositoryCommand.save(scheduleOld);
       scheduleRepositoryCommand.save(scheduleNew);

    }

    @Override
    public void updateGroupPaymentId(UUID id, String groupPaymentId) {
        Receipt receipt = receiptRepositoryCommand.findById(id).orElse(null);
        assert receipt != null;
        receipt.setGroupPaymentId(groupPaymentId);
        receiptRepositoryCommand.save(receipt);
    }

    @Override
    public void updateStatus(UUID id, EStatusReceipt eStatusReceipt) {
        Receipt receipt = receiptRepositoryCommand.findById(id).orElse(null);
        assert receipt != null;
        receipt.setStatus(eStatusReceipt);
        receiptRepositoryCommand.save(receipt);
    }

    @Override
    public void updateResource(UUID id, UUID resourceId) {
        Receipt receipt = receiptRepositoryCommand.findById(id).orElse(null);
        assert receipt != null;
        Resource resource = this.resourceRepositoryQuery.findById(resourceId).orElse(null);
        receipt.getSchedule().setResource(resource);
        receiptRepositoryCommand.save(receipt);
    }

    private void resetSchedule(Receipt receipt) {
        if (receipt.getSchedule() != null) {
            receipt.getSchedule().setStatus(EStatusSchedule.AVAILABLE);
            receipt.getSchedule().setStock(receipt.getSchedule().getStock() + 1);
        }
    }

}
