package com.kynsof.treatments.application.command.optometryExam.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateOptometryExamCommand implements ICommand {

    private UUID id;
    private final UUID externalConsultationId;
    private final String sphereOd;
    private final String cylinderOd;
    private final String axisOd;
    private final String avscOd;
    private final String avccOd;
    private final String sphereOi;
    private final String cylinderOi;
    private final String axisOi;
    private final String avscOi;
    private final String avccOi;
    private final String addPower;
    private final String dp;
    private final String dv;
    private final String filter;
    private boolean isCurrent;
    private final String avccAdd;
    private final String sphereAdd;
    private final String cylinderAdd;

    public CreateOptometryExamCommand(UUID externalConsultationId, String sphereOd, String cylinderOd, String axisOd, String avscOd,
                                      String avccOd, String sphereOi, String cylinderOi, String axisOi, String avscOi,
                                      String avccOi, String addPower, String dp, String dv, String filter,
                                      boolean isCurrent, String avccAdd, String sphereAdd, String cylinderAdd) {
        this.externalConsultationId = externalConsultationId;
        this.sphereOd = sphereOd;
        this.cylinderOd = cylinderOd;
        this.axisOd = axisOd;
        this.avscOd = avscOd;
        this.avccOd = avccOd;
        this.sphereOi = sphereOi;
        this.cylinderOi = cylinderOi;
        this.axisOi = axisOi;
        this.avscOi = avscOi;
        this.avccOi = avccOi;
        this.addPower = addPower;
        this.dp = dp;
        this.dv = dv;
        this.filter = filter;
        this.isCurrent = isCurrent;
        this.avccAdd = avccAdd;
        this.sphereAdd = sphereAdd;
        this.cylinderAdd = cylinderAdd;
    }

    public static CreateOptometryExamCommand fromRequest(CreateOptometryExamRequest request) {
        return new CreateOptometryExamCommand(
                request.getExternalConsultationId(),
                request.getSphereOd(), request.getCylinderOd(), request.getAxisOd(), request.getAvscOd(),
                request.getAvccOd(), request.getSphereOi(), request.getCylinderOi(), request.getAxisOi(),
                request.getAvscOi(), request.getAvccOi(), request.getAddPower(), request.getDp(),
                request.getDv(), request.getFilter(), request.isCurrent(),request.getCylinderAdd()
                , request.getSphereAdd(), request.getCylinderAdd() );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateOptometryExamMessage(id);
    }
}