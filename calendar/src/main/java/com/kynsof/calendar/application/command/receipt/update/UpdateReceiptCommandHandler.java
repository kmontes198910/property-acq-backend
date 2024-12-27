package com.kynsof.calendar.application.command.receipt.update;

import com.kynsof.calendar.domain.dto.ReceiptDto;
import com.kynsof.calendar.domain.dto.ScheduleDto;
import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import com.kynsof.calendar.domain.service.IReceiptService;
import com.kynsof.calendar.domain.service.IScheduleService;
import com.kynsof.calendar.infrastructure.entity.Receipt;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdateReceiptCommandHandler implements ICommandHandler<UpdateReceiptCommand> {

    private final IReceiptService receiptService;
    private final IScheduleService serviceSchedule;
  //  private final IPaymentServiceClient paymentServiceClient;

    public UpdateReceiptCommandHandler(IReceiptService service, IScheduleService scheduleService
        //    , IPaymentServiceClient paymentServiceClient
    ) {
        this.receiptService = service;
        this.serviceSchedule = scheduleService;
      //  this.paymentServiceClient = paymentServiceClient;
    }

    @Override
    public void handle(UpdateReceiptCommand command) {
        Optional<Receipt> entity = this.receiptService.findByRequestId(command.getRequestId());

        if (entity.isPresent()) {
            Receipt receipt = entity.get();
            ReceiptDto dto = receipt.toAggregate();
            ScheduleDto _schedule = serviceSchedule.findById(receipt.getSchedule().getId());

            if (!dto.getStatus().toString().equals(command.getStatus())
                    && command.getStatus().equals(EStatusReceipt.PAYMENT.toString())){
                dto.setStatus(EStatusReceipt.PAYMENT);
//                TransactionsState transactionsState = paymentServiceClient.getTransactionsState(Integer.parseInt(command.getRequestId()));
//                dto.setAuthorizationCode(transactionsState.getValue().getAuthorization());
              //  _schedule.setStatus(EStatusSchedule.ATTENDED);
               // serviceSchedule.update(_schedule);
                //TODO
                //Enviar correo
            }

            if(!dto.getStatus().toString().equals(command.getStatus())
                    && command.getStatus().equals(EStatusReceipt.CANCEL.toString())){
                //TO DO
                //Liverar el stock
                //Enviar Correo de cancelado
                dto.setStatus(EStatusReceipt.CANCEL);
                cleanStock(_schedule);

            }
            if (!dto.getStatus().toString().equals(command.getStatus())
                    && command.getStatus().equals(EStatusReceipt.REJECTED.toString())){
                //TO DO
                //Validar el estado del pago, si el estado es pendiente de pago o pago hacer el proceso de confirmado ,sino cambiar el estado
                 cleanStock(_schedule);

                //Enviar Correo de cancelado
                dto.setStatus(EStatusReceipt.REJECTED);

            }
        receiptService.update(dto);
    }

    }
    private void cleanStock(ScheduleDto scheduleDto){
        scheduleDto.setStock(scheduleDto.getStock()+1);
        serviceSchedule.update(scheduleDto);
    }
}
