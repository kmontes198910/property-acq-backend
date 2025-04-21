package com.kynsof.identity.application.command.business.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateBusinessCommand implements ICommand {

    private UUID id;
    private String name;
    private String latitude;
    private String longitude;
    private String description;
    private String image;
    private String ruc;
    private String address;
    private UUID geographicLocation;
    private final String phone;
    private final String email;
    private String webSite;
    private String storageCapacity;

    public CreateBusinessCommand(String name, String latitude, String longitude, String description, String logo,
                                 String ruc, UUID geographicLocation, String address, String phone, String email,
                                 String webSite, String storageCapacity) {
        this.phone = phone;
        this.email = email;
        this.id = UUID.randomUUID();
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.image = logo;
        this.ruc = ruc;
        this.geographicLocation = geographicLocation;
        this.address = address;
        this.webSite = webSite;
        this.storageCapacity = storageCapacity;
    }

    public static CreateBusinessCommand fromRequest(CreateBusinessRequest request) {
        return new CreateBusinessCommand(
                request.getName(), 
                request.getLatitude(), 
                request.getLongitude(), 
                request.getDescription(), 
                request.getImage(),
                request.getRuc(), 
                request.getGeographicLocation(),
                request.getAddress(),
                request.getPhone(),
                request.getEmail(),
                request.getWebSite(),
                request.getStorageCapacity()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateBusinessMessage(id);
    }
}
