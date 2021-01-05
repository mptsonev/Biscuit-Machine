package com.misho.biscuit.biscuitmachine.components.conveyor;

import java.util.Arrays;

import com.misho.biscuit.biscuitmachine.common.BiscuitState;
import com.misho.biscuit.biscuitmachine.common.MachineEvent;
import com.misho.biscuit.biscuitmachine.common.Observable;
import com.misho.biscuit.biscuitmachine.components.motor.MotorEvent;

public class PulsingConveyor extends Observable<MachineEvent> implements IConveyor {

    private BiscuitState[] conveyorBiscuits;

    public PulsingConveyor() {
        conveyorBiscuits = new BiscuitState[6];
        Arrays.fill(conveyorBiscuits, BiscuitState.NONE);
    }

    @Override
    public void move() {
        if (conveyorBiscuits[conveyorBiscuits.length - 1] == BiscuitState.STAMPED) {
            notify(MachineEvent.BISCUIT_READY);
        }
        var newState = new BiscuitState[6];
        newState[0] = BiscuitState.NONE;
        for (int i = 0; i < conveyorBiscuits.length - 1; i++) {
            newState[i + 1] = conveyorBiscuits[i];
        }
        conveyorBiscuits = newState;
        notify(MachineEvent.CONVEYOR_PULSED);
    }

    @Override
    public void observe(MotorEvent event) {
        if (event == MotorEvent.PULSE) {
            this.move();
        }
    }

    @Override
    public void createBiscuit() {
        conveyorBiscuits[0] = BiscuitState.CREATED;
        notify(MachineEvent.BISCUIT_CREATED);
    }

    @Override
    public void stampBiscuit() {
        if (conveyorBiscuits[2] == BiscuitState.CREATED) {
            conveyorBiscuits[2] = BiscuitState.STAMPED;
            notify(MachineEvent.BISCUIT_STAMPED);
        }
    }

    @Override
    public boolean isEmpty() {
        return Arrays.stream(conveyorBiscuits).filter(state -> state != BiscuitState.NONE).findAny().isEmpty();
    }

    @Override
    public BiscuitState[] getState() {
        return conveyorBiscuits;
    }

}
