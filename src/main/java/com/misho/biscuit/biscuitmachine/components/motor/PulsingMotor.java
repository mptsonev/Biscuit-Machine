package com.misho.biscuit.biscuitmachine.components.motor;

import com.misho.biscuit.biscuitmachine.common.Observable;

public class PulsingMotor extends Observable<MotorEvent> implements IMotor {

    private volatile MotorStatus status;
    private MotorRunner runner;

    public PulsingMotor() {
        this.status = MotorStatus.STOPPED;
    }

    @Override
    public synchronized void start() {
        if (status == MotorStatus.STOPPED) {
            status = MotorStatus.RUNNING;
            runner = new MotorRunner(() -> this.notify(MotorEvent.PULSE));
            runner.start();
        }
    }

    @Override
    public synchronized void stop() {
        if (status == MotorStatus.RUNNING) {
            status = MotorStatus.STOPPED;
            runner.interrupt();
        }
    }

    @Override
    public MotorStatus getStatus() {
        return status;
    }

}
