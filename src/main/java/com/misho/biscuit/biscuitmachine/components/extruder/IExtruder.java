package com.misho.biscuit.biscuitmachine.components.extruder;

import com.misho.biscuit.biscuitmachine.common.Observer;
import com.misho.biscuit.biscuitmachine.components.motor.MotorEvent;

public interface IExtruder extends Observer<MotorEvent> {

    public void extrude();
    
    public void stop();
    
    public void start();
    
    public ExtruderStatus getStatus();

}
