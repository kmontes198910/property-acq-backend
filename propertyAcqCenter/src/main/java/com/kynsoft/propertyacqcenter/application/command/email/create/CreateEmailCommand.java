package com.kynsoft.propertyacqcenter.application.command.email.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateEmailCommand implements ICommand {
    private String email;
    private String name;
    private String lastName;
    private String pass;

    public static CreateEmailCommand fromRequest(CreateEmailRequest request) {
        return CreateEmailCommand
                .builder()
                .email(request.getEmail())
                .name(request.getName())
                .lastName(request.getLastName())
                .name(request.getName())
                .pass(request.getPass())
                .build();
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateEmailMessage();
    }
}
