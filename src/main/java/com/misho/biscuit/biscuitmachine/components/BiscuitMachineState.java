package com.misho.biscuit.biscuitmachine.components;

import com.misho.biscuit.biscuitmachine.common.BiscuitState;
import com.misho.biscuit.biscuitmachine.components.motor.MotorStatus;
import com.misho.biscuit.biscuitmachine.components.oven.OvenStatus;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class BiscuitMachineState {

    private int readyBiscuits;
    private MotorStatus motorStatus;
    private OvenStatus ovenStatus;
    private int ovenDegrees;
    private BiscuitMachineStatus machineStatus;
    private BiscuitState[] conveyorState;

}
