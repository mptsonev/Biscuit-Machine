package com.misho.biscuit.biscuitmachine.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.misho.biscuit.biscuitmachine.TestObserver;
import com.misho.biscuit.biscuitmachine.common.Constants;
import com.misho.biscuit.biscuitmachine.common.MachineEvent;
import com.misho.biscuit.biscuitmachine.components.conveyor.IConveyor;
import com.misho.biscuit.biscuitmachine.components.extruder.IExtruder;
import com.misho.biscuit.biscuitmachine.components.motor.IMotor;
import com.misho.biscuit.biscuitmachine.components.oven.IOven;
import com.misho.biscuit.biscuitmachine.components.stamper.IStamper;

public class BiscuitMachineTest extends TestObserver<BiscuitMachineState> {

    @Mock
    private IMotor motor;
    @Mock
    private IOven oven;
    @Mock
    private IConveyor conveyor;
    @Mock
    private IExtruder extruder;
    @Mock
    private IStamper stamper;
    @Mock
    private BiscuitMachineStopSequenceCallback callback;

    private BiscuitMachine machine;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.machine = BiscuitMachine.builder().motor(motor).oven(oven).conveyor(conveyor).extruder(extruder)
                .stamper(stamper).build();
        this.machine.subscribe(this);
    }

    @Test
    public void testStart() {
        machine.start();
        // Make sure multiple starts are idempotent
        machine.start();
        verify(oven, times(1)).start(Constants.OVEN_REQUIRED_DEGREES);
        assertEquals(BiscuitMachineStatus.RUNNING, machine.getStatus());
        checkEvents(getExpectedState(0, 0, BiscuitMachineStatus.RUNNING), 1);
    }

    @Test
    public void testPause() {
        machine.pause();
        machine.pause();
        // Make sure multiple pauses are idempotent
        assertEquals(BiscuitMachineStatus.PAUSED, machine.getStatus());
        checkEvents(getExpectedState(0, 0, BiscuitMachineStatus.PAUSED), 1);
        // Check if pause sequence initiated
        var order = Mockito.inOrder(motor, oven, extruder, stamper);
        order.verify(motor).stop();
        order.verify(oven).pause();
        order.verify(extruder).stop();
        order.verify(stamper).stop();
    }

    @Test
    public void testStop() throws InterruptedException {
        doReturn(true).when(conveyor).isEmpty();
        machine.start();
        checkEvents(getExpectedState(0, 0, BiscuitMachineStatus.RUNNING), 1);
        machine.stop();
        // Make sure multiple pauses are idempotent
        machine.stop();
        verify(oven, times(1)).pause();
        verify(extruder, times(1)).stop();
        assertEquals(BiscuitMachineStatus.STOPPING, machine.getStatus());

        checkEvents(getExpectedState(0, 0, BiscuitMachineStatus.STOPPING), 1);
        Thread.sleep(100);

        var order = Mockito.inOrder(oven, motor, stamper, callback);
        order.verify(motor).stop();
        order.verify(stamper).stop();
        order.verify(oven).stop();
        checkEvents(getExpectedState(0, 0, BiscuitMachineStatus.STOPPED), 1);

    }

    @Test
    public void testStopFromPaused() {
        machine.pause();
        checkEvents(getExpectedState(0, 0, BiscuitMachineStatus.PAUSED), 1);
        machine.stop();
        verify(stamper, times(1)).start();
        verify(motor, times(1)).start();
        checkEvents(getExpectedState(0, 0, BiscuitMachineStatus.STOPPING), 1);
    }

    @Test
    public void testStartOnOvenReady() {
        machine.observe(MachineEvent.OVEN_READY);
        var order = Mockito.inOrder(motor, extruder, stamper);
        order.verify(motor).start();
        order.verify(extruder).start();
        order.verify(stamper).start();
        checkEvents(getExpectedState(0, 0, BiscuitMachineStatus.STOPPED), 1);
    }

    @Test
    public void testBiscuitAddedOnReady() {
        machine.observe(MachineEvent.BISCUIT_READY);
        checkEvents(getExpectedState(1, 0, BiscuitMachineStatus.STOPPED), 1);
    }

    private BiscuitMachineState getExpectedState(int expectedBiscuits, int expectedOvenDegrees,
            BiscuitMachineStatus expectedStatus) {
        return new BiscuitMachineState(expectedBiscuits, motor.getStatus(), oven.getStatus(), expectedOvenDegrees,
                expectedStatus, conveyor.getState());
    }

}
