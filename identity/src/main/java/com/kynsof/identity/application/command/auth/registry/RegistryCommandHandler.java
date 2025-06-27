package com.kynsof.identity.application.command.auth.registry;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsof.identity.domain.dto.UserStatus;
import com.kynsof.identity.domain.dto.UserSystemDto;
import com.kynsof.identity.domain.interfaces.service.IAuthService;
import com.kynsof.identity.domain.interfaces.service.IUserSystemService;
import com.kynsof.share.core.domain.EUserType;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.UserEmailDifferentException;
import com.kynsof.share.core.domain.response.ErrorField;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.UUID;

@Component
public class RegistryCommandHandler implements ICommandHandler<RegistryCommand> {
    private final IAuthService authService;
    private final IUserSystemService userSystemService;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    public RegistryCommandHandler(IAuthService authService, IUserSystemService userSystemService) {
        this.authService = authService;
        this.userSystemService = userSystemService;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void handle(RegistryCommand command) {
        try {
            // Llamada al servicio externo para validar si el usuario existe y obtener todos sus datos
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://patients-service:80/api/patients/identification/" + command.getUsername()))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                PatientsResponse userResponse = objectMapper.readValue(response.body(), PatientsResponse.class);
                if (userResponse != null && userResponse.getId() != null) {
                    if (!Objects.equals(userResponse.getEmail().toUpperCase(), command.getEmail().toUpperCase())) {
                        throw new UserEmailDifferentException("El correo es diferente al registrado en el sistema", new ErrorField("email", "El correo es diferente al registrado en el sistema"));
                    }
                    System.out.println("El usuario ya existe con la identificación: " + userResponse.getIdentification() + " y tiene el ID: " + userResponse.getId());
                    String registerUser = authService.registerUser(new UserRequest(
                            command.getUsername(), command.getEmail(),command.getFirstname(),
                            command.getLastname(),command.getPassword()
                    ), false);

                    UserSystemDto userDto = new UserSystemDto(
                            userResponse.getId(),
                            command.getUsername(),
                            command.getEmail(),
                            command.getFirstname(),
                            command.getLastname(),
                            UserStatus.ACTIVE,
                            userResponse.getImage()
                    );
                    userDto.setKeyCloakId(UUID.fromString(registerUser));
                    userDto.setUserType(EUserType.PATIENTS);

                    UUID id = userSystemService.create(userDto);
                    changeKeycloackId(userResponse.getId(), registerUser);

                    command.setResul(id.toString());
                    return;
                }
            }
        } catch (UserEmailDifferentException e) {
            throw new UserEmailDifferentException("El correo es diferente al registrado en el sistema", new ErrorField("email", "El correo es diferente al registrado en el sistema"));
        }
        catch (InterruptedException | IOException e){
            System.err.println("Error en la llamada HTTP: " + e.getMessage());
        }
        String registerUser = authService.registerUser(new UserRequest(
                command.getUsername(), command.getEmail(), command.getFirstname(),
                command.getLastname(), command.getPassword()
        ), false);
        command.setResul(registerUser);

        UserSystemDto userDto = new UserSystemDto(
                UUID.fromString(registerUser),
                command.getUsername(),
                command.getEmail(),
                command.getFirstname(),
                command.getLastname(),
                UserStatus.ACTIVE,
                ""
        );
        userDto.setKeyCloakId(UUID.fromString(registerUser));
        userDto.setUserType(EUserType.PATIENTS);

        UUID id = userSystemService.create(userDto);


//        welcomeMessageProducer.sendWelcomeMessage(command.getEmail(),
//                command.getFirstname(), command.getLastname(),
//                command.getPassword());
    }

    private void changeKeycloackId(UUID userResponseId, String registerUser) throws IOException, InterruptedException {
        String url = String.format("http://patients-service:80/api/patients/setKeycloak/%s/%s", userResponseId, registerUser);
        HttpRequest requestUpdateKeyCloak = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> responseUpdate = httpClient.send(requestUpdateKeyCloak, HttpResponse.BodyHandlers.ofString());
    }


}
