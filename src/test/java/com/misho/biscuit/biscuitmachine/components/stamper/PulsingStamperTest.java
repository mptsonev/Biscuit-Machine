package com.misho.biscuit.biscuitmachine.components.stamper;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.misho.biscuit.biscuitmachine.TestObserver;
import com.misho.biscuit.biscuitmachine.common.BiscuitState;
import com.misho.biscuit.biscuitmachine.common.MachineEvent;
import com.misho.biscuit.biscuitmachine.components.conveyor.IConveyor;
import com.misho.biscuit.biscuitmachine.components.conveyor.PulsingConveyor;
import com.misho.biscuit.biscuitmachine.components.motor.MotorEvent;

public class PulsingStamperTest extends TestObserver<MachineEvent> {

    private IStamper stamper;
    private IConveyor conveyor;

    @BeforeEach
    public void setUp() {
        this.conveyor = new PulsingConveyor();
        this.conveyor.subscribe(this);
        this.stamper = new PulsingStamper(conveyor);
    }

    @Test
    public void testStamp() {
        conveyor.getState()[2] = BiscuitState.CREATED;
        assertEquals(StamperStatus.PREPARING, stamper.getStatus());
        stamper.start();
        stamper.observe(MotorEvent.PULSE);
        assertEquals(StamperStatus.STAMPING, stamper.getStatus());
        stamper.observe(MotorEvent.PULSE);
        assertEquals(StamperStatus.PREPARING, stamper.getStatus());
        assertEquals(BiscuitState.STAMPED, conveyor.getState()[2]);
        checkEvents(MachineEvent.BISCUIT_STAMPED, 1);

    }

    @Test
    public void testStampEmpty() {
        assertEquals(StamperStatus.PREPARING, stamper.getStatus());
        var initial = conveyor.getState();
        stamper.start();
        for (int i = 0; i < 3; i++) {
            stamper.observe(MotorEvent.PULSE);
        }
        assertArrayEquals(initial, conveyor.getState());
    }

    @Test
    public void testStopStamp() {
        conveyor.getState()[2] = BiscuitState.CREATED;
        var initial = conveyor.getState();
        assertEquals(StamperStatus.PREPARING, stamper.getStatus());
        stamper.stop();
        for (int i = 0; i < 3; i++) {
            stamper.observe(MotorEvent.PULSE);
            assertEquals(StamperStatus.PREPARING, stamper.getStatus());
        }
        assertArrayEquals(initial, conveyor.getState());
        assertEquals(StamperStatus.PREPARING, stamper.getStatus());

    }
}
