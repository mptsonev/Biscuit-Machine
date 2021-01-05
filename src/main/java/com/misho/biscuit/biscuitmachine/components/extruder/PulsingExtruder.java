package com.misho.biscuit.biscuitmachine.components.extruder;

import com.misho.biscuit.biscuitmachine.components.conveyor.IConveyor;
import com.misho.biscuit.biscuitmachine.components.motor.MotorEvent;

public class PulsingExtruder implements IExtruder {

    private IConveyor conveyor;
    private boolean canExtrude;
    private ExtruderStatus status;

    public PulsingExtruder(IConveyor conveyor) {
        this.conveyor = conveyor;
        this.status = ExtruderStatus.PREPARING;
    }

    @Override
    public void extrude() {
        conveyor.createBiscuit();
        status = ExtruderStatus.EXTRUDING;
    }

    @Override
    public void observe(MotorEvent event) {
        if (canExtrude && status == ExtruderStatus.PREPARING && event == MotorEvent.PULSE) {
            extrude();
        } else {
            status = ExtruderStatus.PREPARING;
        }
    }

    @Override
    public void stop() {
        this.canExtrude = false;
    }

    @Override
    public void start() {
        this.canExtrude = true;
    }

    @Override
    public ExtruderStatus getStatus() {
        return status;
    }

}
