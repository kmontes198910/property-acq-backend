package com.kynsoft.medicaltest.application.command.labTestItemRequest.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class DeleteLabTestItemRequestMessage implements ICommandMessage {

    private String message;

    public DeleteLabTestItemRequestMessage() {
        this.message = "DELETE_LAB_TEST_ITEM_REQUEST";
    }
}
