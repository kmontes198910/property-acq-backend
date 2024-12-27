package com.kynsof.identity.application.command.walletTransaction.create;

import com.kynsof.identity.domain.dto.UserSystemDto;
import com.kynsof.identity.domain.dto.WalletDto;
import com.kynsof.identity.domain.dto.WalletTransactionDto;
import com.kynsof.identity.domain.dto.enumType.TransactionType;
import com.kynsof.identity.domain.interfaces.service.IUserSystemService;
import com.kynsof.identity.domain.interfaces.service.IWalletService;
import com.kynsof.identity.domain.interfaces.service.IWalletTransactionService;
//import com.kynsof.share.core.application.payment.domain.placeToPlay.response.TransactionsState;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CreateWalletTransactionCommandHandler implements ICommandHandler<CreateWalletTransactionCommand> {

    private final IUserSystemService customerService;
    private final IWalletTransactionService walletTransactionService;
    private final IWalletService walletService;
   // private final IPaymentServiceClient paymentServiceClient;


    public CreateWalletTransactionCommandHandler(IUserSystemService service,
                                                 IWalletTransactionService walletTransactionService,
                                                 IWalletService walletService//,
                                                // IPaymentServiceClient paymentServiceClient
    ) {
        this.customerService = service;
        this.walletTransactionService = walletTransactionService;
        this.walletService = walletService;
      //  this.paymentServiceClient = paymentServiceClient;
    }

    @Override
    public void handle(CreateWalletTransactionCommand command) {
//        TransactionsState transactionsState = paymentServiceClient.getTransactionsState(Integer.parseInt(command.getRequestId()));
//        if(transactionsState == null || !transactionsState.getValue().getStatus().getStatus().equals("APPROVED")) {
//            throw new BusinessException(DomainErrorMessage.PAYMENT_NOT_FOUND, DomainErrorMessage.PAYMENT_NOT_FOUND.toString());
//        }
        UserSystemDto customerDto = customerService.findById(command.getCustomerId());

        WalletDto walletDto = walletService.findByCustomerId(customerDto.getId());
        if (walletDto == null) {

            WalletDto value = new WalletDto(UUID.randomUUID(), command.getAmount(), customerDto, null);
            UUID id = walletService.create(value);
            value.setId(id);
            WalletTransactionDto walletTransactionDto = new WalletTransactionDto();
            walletTransactionDto.setId(UUID.randomUUID());
            walletTransactionDto.setWalletDto(value);
            walletTransactionDto.setWalletId(value.getId());
            walletTransactionDto.setAmount(command.getAmount());
            walletTransactionDto.setType(TransactionType.DEPOSIT);
            walletTransactionDto.setTransactionDate(LocalDateTime.now());
            walletTransactionDto.setDescription(command.getDescription());
            walletTransactionDto.setAuthorizationCode(command.getAuthorizationCode());
            walletTransactionDto.setRequestId(command.getRequestId());
            UUID result = walletTransactionService.create(walletTransactionDto);
            command.setId(result);

        } else {
            walletDto.setBalance(walletDto.getBalance().add(command.getAmount()));
            WalletTransactionDto walletTransactionDto = new WalletTransactionDto();
            walletTransactionDto.setId(UUID.randomUUID());
            walletTransactionDto.setWalletDto(walletDto);
            walletTransactionDto.setWalletId(walletDto.getId());
            walletTransactionDto.setAmount(command.getAmount());
            walletTransactionDto.setType(TransactionType.DEPOSIT);
            walletTransactionDto.setTransactionDate(LocalDateTime.now());
            walletTransactionDto.setDescription(command.getDescription());
            walletTransactionDto.setAuthorizationCode(command.getAuthorizationCode());
            walletTransactionDto.setRequestId(command.getRequestId());
            UUID result = walletTransactionService.create(walletTransactionDto);
            command.setId(result);
        }

    }
}