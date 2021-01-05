package com.misho.biscuit.biscuitmachine.components.extruder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.misho.biscuit.biscuitmachine.common.BiscuitState;
import com.misho.biscuit.biscuitmachine.components.conveyor.IConveyor;
import com.misho.biscuit.biscuitmachine.components.conveyor.PulsingConveyor;
import com.misho.biscuit.biscuitmachine.components.motor.MotorEvent;

public class PulsingExtruderTest {

    private IExtruder extruder;
    private IConveyor conveyor;

    @BeforeEach
    public void setUp() {
        this.conveyor = new PulsingConveyor();
        this.extruder = new PulsingExtruder(conveyor);
    }

    @Test
    public void testExtrude() {
        assertEquals(ExtruderStatus.PREPARING, extruder.getStatus());
        extruder.start();
        extruder.observe(MotorEvent.PULSE);
        assertEquals(ExtruderStatus.EXTRUDING, extruder.getStatus());
        assertEquals(BiscuitState.CREATED, conveyor.getState()[0]);
        extruder.observe(MotorEvent.PULSE);
        assertEquals(ExtruderStatus.PREPARING, extruder.getStatus());
        assertEquals(BiscuitState.CREATED, conveyor.getState()[0]);

    }
    
    @Test
    public void testStopped() {
        extruder.stop();
        for (int i = 0; i < 3; i++) {
            extruder.observe(MotorEvent.PULSE);
            assertEquals(ExtruderStatus.PREPARING, extruder.getStatus());
            assertEquals(BiscuitState.NONE, conveyor.getState()[0]);
        }
    }

}
