package com.misho.biscuit.biscuitmachine.components.oven;

import com.misho.biscuit.biscuitmachine.common.MachineEvent;
import com.misho.biscuit.biscuitmachine.common.IObservable;

public interface IOven extends IObservable<MachineEvent> {

    public void start(int degrees);

    public void pause();

    public void stop();

    public int getDegrees();

    public OvenStatus getStatus();

}
