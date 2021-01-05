package com.misho.biscuit.biscuitmachine.components;

import com.misho.biscuit.biscuitmachine.components.motor.IMotor;
import com.misho.biscuit.biscuitmachine.components.oven.IOven;
import com.misho.biscuit.biscuitmachine.components.stamper.IStamper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BiscuitMachineStopSequence implements BiscuitMachineSequence {

    private IOven oven;
    private IMotor motor;
    private IStamper stamper;
    private BiscuitMachineStopSequenceCallback callback;

    @Override
    public void execute() {
        motor.stop();
        stamper.stop();
        oven.stop();
        callback.callBack();
    }

}
