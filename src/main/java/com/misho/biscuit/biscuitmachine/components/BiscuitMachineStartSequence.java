package com.misho.biscuit.biscuitmachine.components;

import com.misho.biscuit.biscuitmachine.components.extruder.IExtruder;
import com.misho.biscuit.biscuitmachine.components.motor.IMotor;
import com.misho.biscuit.biscuitmachine.components.stamper.IStamper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BiscuitMachineStartSequence implements BiscuitMachineSequence {

    private IMotor motor;
    private IExtruder extruder;
    private IStamper stamper;

    @Override
    public void execute() {
        motor.start();
        extruder.start();
        stamper.start();
    }

}
