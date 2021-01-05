package com.misho.biscuit.biscuitmachine.components;

import com.misho.biscuit.biscuitmachine.common.IObservable;
import com.misho.biscuit.biscuitmachine.common.MachineEvent;
import com.misho.biscuit.biscuitmachine.common.Observer;

public interface IBiscuitMachine extends Observer<MachineEvent>, IObservable<BiscuitMachineState> {

    public void start();

    public void stop();

    public void pause();

    public BiscuitMachineStatus getStatus();
    
    public BiscuitMachineState getState();
}
