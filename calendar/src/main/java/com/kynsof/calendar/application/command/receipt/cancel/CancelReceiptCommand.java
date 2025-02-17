//package com.kynsof.calendar.application.command.receipt.cancel;
//
//import com.kynsof.share.core.domain.bus.command.ICommand;
//import com.kynsof.share.core.domain.bus.command.ICommandMessage;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.UUID;
//
//@Getter
//@Setter
//public class CancelReceiptCommand implements ICommand {
//
//    private final UUID userId;
//    private final UUID receiptId;
//    private final String ipAddress;
//    private final String userAgent;
//
//    public CancelReceiptCommand(UUID receiptId, UUID userId,  String ipAddress, String userAgent) {
//        this.userId = userId;
//        this.receiptId = receiptId;
//        this.ipAddress = ipAddress;
//        this.userAgent = userAgent;
//    }
//
//    public static CancelReceiptCommand fromRequest(CancelReceiptRequest request, String ipAddress, String userAgent) {
//        return new CancelReceiptCommand(request.getReceiptId(), request.getUserId(),  ipAddress,
//                userAgent );
//    }
//
//    @Override
//    public ICommandMessage getMessage() {
//        return new CancelReceiptMessage(receiptId);
//    }
//}
