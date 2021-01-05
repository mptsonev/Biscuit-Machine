package com.misho.biscuit.biscuitmachine.components.oven;

import com.misho.biscuit.biscuitmachine.common.IObservable;

public interface IOvenHeater extends IObservable<OvenHeatChange> {

    public void start();

    public void stop();

    public void pause();

}
