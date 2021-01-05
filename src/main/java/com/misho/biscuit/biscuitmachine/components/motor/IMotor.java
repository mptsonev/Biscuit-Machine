package com.misho.biscuit.biscuitmachine.components.motor;

import com.misho.biscuit.biscuitmachine.common.IObservable;

public interface IMotor extends IObservable<MotorEvent> {

    public void start();

    public void stop();

    public MotorStatus getStatus();

}
