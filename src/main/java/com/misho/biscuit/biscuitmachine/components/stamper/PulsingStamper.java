package com.misho.biscuit.biscuitmachine.components.stamper;

import com.misho.biscuit.biscuitmachine.common.BiscuitState;
import com.misho.biscuit.biscuitmachine.components.conveyor.IConveyor;
import com.misho.biscuit.biscuitmachine.components.motor.MotorEvent;

public class PulsingStamper implements IStamper {

    private IConveyor conveyor;
    private boolean canStamp;
    private StamperStatus status;

    public PulsingStamper(IConveyor conveyor) {
        this.conveyor = conveyor;
        this.status = StamperStatus.PREPARING;
    }

    @Override
    public void stamp() {
        if (conveyor.getState()[2] == BiscuitState.CREATED) {
            conveyor.stampBiscuit();
            status = StamperStatus.STAMPING;
        }
    }

    @Override
    public void observe(MotorEvent event) {
        if (canStamp && status == StamperStatus.PREPARING) {
            stamp();
        } else {
            status = StamperStatus.PREPARING;
        }
    }

    @Override
    public void stop() {
        this.canStamp = false;
        this.status = StamperStatus.PREPARING;
    }

    @Override
    public void start() {
        this.canStamp = true;
        this.status = StamperStatus.PREPARING;
    }

    @Override
    public StamperStatus getStatus() {
        return status;
    }

}
