package com.kynsof.treatments.application.command.optometryExam.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateOptometryExamCommand implements ICommand {
    private final UUID id;
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
    private final Boolean isCurrent;
    private final String avccAdd;
    private final String sphereAdd;
    private final String cylinderAdd;
    private final String avscAdd;
    private final String axisAdd;

    public UpdateOptometryExamCommand(UUID id, String sphereOd, String cylinderOd, String axisOd, String avscOd,
                                      String avccOd, String sphereOi, String cylinderOi, String axisOi, String avscOi,
                                      String avccOi, String addPower, String dp, String dv, String filter, Boolean isCurrent, String avccAdd, String sphereAdd, String cylinderAdd, String avscAdd, String axisAdd) {
        this.id = id;
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
        this.avscAdd = avscAdd;
        this.axisAdd = axisAdd;
    }

    public static UpdateOptometryExamCommand fromRequest(UpdateOptometryExamRequest request, UUID id) {
        return new UpdateOptometryExamCommand(
                id,
                request.getSphereOd(),
                request.getCylinderOd(),
                request.getAxisOd(),
                request.getAvscOd(),
                request.getAvccOd(),
                request.getSphereOi(),
                request.getCylinderOi(),
                request.getAxisOi(),
                request.getAvscOi(),
                request.getAvccOi(),
                request.getAddPower(),
                request.getDp(),
                request.getDv(),
                request.getFilter(),
                request.isCurrent(),
                request.getAvccAdd(),
                request.getSphereAdd(),
                request.getCylinderAdd(),
                request.getAvscAdd(),
                request.getAxisAdd());
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateOptometryExamMessage(id);
    }
}