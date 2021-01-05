package com.misho.biscuit.biscuitmachine.components.conveyor;

import com.misho.biscuit.biscuitmachine.common.BiscuitState;
import com.misho.biscuit.biscuitmachine.common.IObservable;
import com.misho.biscuit.biscuitmachine.common.MachineEvent;
import com.misho.biscuit.biscuitmachine.common.Observer;
import com.misho.biscuit.biscuitmachine.components.motor.MotorEvent;

public interface IConveyor extends IObservable<MachineEvent>, Observer<MotorEvent> {

    public void move();

    public void createBiscuit();

    public boolean isEmpty();

    public BiscuitState[] getState();

    public void stampBiscuit();

}
