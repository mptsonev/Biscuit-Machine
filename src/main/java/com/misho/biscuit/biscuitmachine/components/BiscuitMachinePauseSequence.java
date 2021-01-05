package com.misho.biscuit.biscuitmachine.components;

import com.misho.biscuit.biscuitmachine.components.extruder.IExtruder;
import com.misho.biscuit.biscuitmachine.components.motor.IMotor;
import com.misho.biscuit.biscuitmachine.components.oven.IOven;
import com.misho.biscuit.biscuitmachine.components.stamper.IStamper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BiscuitMachinePauseSequence implements BiscuitMachineSequence {

    private IMotor motor;
    private IOven oven;
    private IExtruder extruder;
    private IStamper stamper;

    @Override
    public void execute() {
        motor.stop();
        oven.pause();
        extruder.stop();
        stamper.stop();
    }

}
