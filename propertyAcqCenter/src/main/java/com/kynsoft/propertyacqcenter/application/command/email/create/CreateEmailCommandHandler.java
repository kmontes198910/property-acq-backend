package com.kynsoft.propertyacqcenter.application.command.email.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.exception.NotDeleteException;
import com.kynsoft.propertyacqcenter.domain.dto.mailjet.MailJetRecipientDto;
import com.kynsoft.propertyacqcenter.domain.dto.mailjet.MailJetVarDto;
import com.kynsoft.propertyacqcenter.domain.dto.mailjet.SendMailJetEmailRequestDto;
import com.kynsoft.propertyacqcenter.domain.services.ICloudBridgesFileService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CreateEmailCommandHandler implements ICommandHandler<CreateEmailCommand> {

    private final ICloudBridgesFileService cloudBridgesService;

    public CreateEmailCommandHandler(ICloudBridgesFileService cloudBridgesService) {
        this.cloudBridgesService = cloudBridgesService;
    }

    @Override
    public void handle(CreateEmailCommand command) {
        SendMailJetEmailRequestDto requestDto = new SendMailJetEmailRequestDto();

        // Configurar destinatario
        List<MailJetRecipientDto> recipients = new ArrayList<>();
        recipients.add(new MailJetRecipientDto(command.getEmail(), command.getLastName() + " " + command.getName()));
        requestDto.setRecipientEmail(recipients);

        List<MailJetVarDto> vars = new ArrayList<>();
        vars.add(new MailJetVarDto("user_name", command.getEmail()));
        vars.add(new MailJetVarDto("temp_password", command.getPass()));

        requestDto.setMailJetVars(vars);

        // Configurar el asunto
        requestDto.setSubject("Bienvenido a Kynsoft - Usuario creado");

        // ID de la plantilla en Mailjet (este es un ejemplo, debe configurarse el ID correcto)
        requestDto.setTemplateId("5965446");

        // Enviar la solicitud
        try {
            cloudBridgesService.sendEmail(requestDto);
        } catch (Exception e) {
            throw new NotDeleteException();
        }
    }

}
