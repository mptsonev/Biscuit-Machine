package com.misho.biscuit.biscuitmachine.components.stamper;

import com.misho.biscuit.biscuitmachine.common.Observer;
import com.misho.biscuit.biscuitmachine.components.motor.MotorEvent;

public interface IStamper extends Observer<MotorEvent> {

    public void stamp();
    
    public void stop();
    
    public void start();
    
    public StamperStatus getStatus();

}
