package com.misho.biscuit.biscuitmachine.components.conveyor;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.misho.biscuit.biscuitmachine.TestObserver;
import com.misho.biscuit.biscuitmachine.common.BiscuitState;
import com.misho.biscuit.biscuitmachine.common.MachineEvent;
import com.misho.biscuit.biscuitmachine.components.motor.MotorEvent;

public class PulsingConveyorTest extends TestObserver<MachineEvent> {

    private IConveyor conveyor;

    @BeforeEach
    public void beforeEach() {
        conveyor = new PulsingConveyor();
        conveyor.subscribe(this);
    }

    @Test
    public void testInitial() {
        var initial = new BiscuitState[6];
        Arrays.fill(initial, BiscuitState.NONE);
        assertArrayEquals(initial, conveyor.getState());
    }

    @Test
    public void testEmptyPulses() {
        var initial = new BiscuitState[6];
        Arrays.fill(initial, BiscuitState.NONE);
        for (int i = 0; i < 5; i++) {
            conveyor.observe(MotorEvent.PULSE);
        }
        assertArrayEquals(initial, conveyor.getState());
        checkEvents(MachineEvent.CONVEYOR_PULSED, 5);
    }

    @Test
    public void testPulseMoving() {
        var initial = new BiscuitState[6];
        Arrays.fill(initial, BiscuitState.NONE);
        initial[0] = BiscuitState.CREATED;
        conveyor.createBiscuit();
        assertArrayEquals(initial, conveyor.getState());
        for (int i = 0; i < 5; i++) {
            conveyor.observe(MotorEvent.PULSE);
        }
        checkEvents(MachineEvent.BISCUIT_CREATED, 1);
        checkEvents(MachineEvent.CONVEYOR_PULSED, 5);
        initial[0] = BiscuitState.NONE;
        initial[5] = BiscuitState.CREATED;
        assertArrayEquals(initial, conveyor.getState());
    }

    @Test
    public void testBiscuitCreated() {
        var initial = new BiscuitState[6];
        Arrays.fill(initial, BiscuitState.NONE);

        conveyor.getState()[5] = BiscuitState.STAMPED;
        conveyor.observe(MotorEvent.PULSE);
        checkEvents(MachineEvent.CONVEYOR_PULSED, 1);
        checkEvents(MachineEvent.BISCUIT_READY, 1);
        assertArrayEquals(initial, conveyor.getState());
    }

    @Test
    public void testStamp() {
        var initial = new BiscuitState[6];
        Arrays.fill(initial, BiscuitState.NONE);
        conveyor.stampBiscuit();
        assertArrayEquals(initial, conveyor.getState());
        conveyor.getState()[2] = BiscuitState.CREATED;
        conveyor.stampBiscuit();
        initial[2] = BiscuitState.STAMPED;
        assertArrayEquals(initial, conveyor.getState());
        checkEvents(MachineEvent.BISCUIT_STAMPED, 1);
    }

    @Test
    public void testEmpty() {
        assertTrue(conveyor.isEmpty());
        conveyor.createBiscuit();
        assertFalse(conveyor.isEmpty());
        conveyor.getState()[0] = BiscuitState.NONE;
        conveyor.getState()[2] = BiscuitState.STAMPED;
        assertFalse(conveyor.isEmpty());
        conveyor.getState()[2] = BiscuitState.NONE;
        conveyor.getState()[5] = BiscuitState.STAMPED;
        assertFalse(conveyor.isEmpty());
        checkEvents(MachineEvent.BISCUIT_CREATED, 1);
    }

}
