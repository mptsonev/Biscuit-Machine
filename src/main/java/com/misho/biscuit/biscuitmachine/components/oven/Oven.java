package com.misho.biscuit.biscuitmachine.components.oven;

import com.misho.biscuit.biscuitmachine.common.MachineEvent;
import com.misho.biscuit.biscuitmachine.common.Observable;
import com.misho.biscuit.biscuitmachine.common.Observer;

public class Oven extends Observable<MachineEvent> implements IOven, Observer<OvenHeatChange> {

    private OvenStatus status;
    private IOvenHeater heater;
    private int ovenDegrees;
    private int wantedDegrees;

    private Oven(IOvenHeater heater) {
        this.status = OvenStatus.STOPPED;
        this.ovenDegrees = 0;
        this.wantedDegrees = 0;
        this.heater = heater;
    }

    @Override
    public synchronized void start(int degrees) {
        if (status != OvenStatus.RUNNING) {
            status = OvenStatus.RUNNING;
            wantedDegrees = degrees;
            if (wantedDegrees != ovenDegrees) {
                heater.start();
            } else {
                notify(MachineEvent.OVEN_READY);
            }
        }
    }

    @Override
    public synchronized void pause() {
        if (status == OvenStatus.RUNNING) {
            status = OvenStatus.PAUSED;
            heater.pause();
        }
    }

    @Override
    public synchronized void stop() {
        if (status != OvenStatus.STOPPED) {
            status = OvenStatus.STOPPED;
            wantedDegrees = 0;
            ovenDegrees = 0;
            heater.stop();
        }
    }

    @Override
    public int getDegrees() {
        return ovenDegrees;
    }

    @Override
    public OvenStatus getStatus() {
        return status;
    }

    @Override
    public void observe(OvenHeatChange event) {
        ovenDegrees += event.getDegreesChange();
        if (ovenDegrees >= wantedDegrees) {
            notify(MachineEvent.OVEN_READY);
            heater.stop();
        } else {
            notify(MachineEvent.OVEN_DEGREES_CHANGE);
        }
    }

    public static OvenBuilder builder() {
        return new OvenBuilder();
    }

    public static class OvenBuilder {
        private IOvenHeater heater;

        public Oven build() {
            var oven = new Oven(heater);
            heater.subscribe(oven);
            return oven;
        }

        public OvenBuilder heater(IOvenHeater heater) {
            this.heater = heater;
            return this;
        }
    }

}
