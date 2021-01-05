package com.misho.biscuit.biscuitmachine.components.oven;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.misho.biscuit.biscuitmachine.TestObserver;
import com.misho.biscuit.biscuitmachine.common.MachineEvent;

public class OvenTest extends TestObserver<MachineEvent> {

    private Oven oven;

    @Mock
    private IOvenHeater heater;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        initMocks();
        oven = Oven.builder().heater(heater).build();
        oven.subscribe(this);
    }

    @Test
    public void testStart() {
        oven.start(200);
        assertEquals(OvenStatus.RUNNING, oven.getStatus());
        verify(heater, times(1)).start();
    }

    @Test
    public void testMultipleStart() {
        oven.start(200);
        oven.start(200);
        oven.start(200);
        assertEquals(OvenStatus.RUNNING, oven.getStatus());
        verify(heater, times(1)).start();
    }

    @Test
    public void testStartAlreadyHeated() {
        oven.observe(new OvenHeatChange(200));
        oven.start(200);
        assertEquals(OvenStatus.RUNNING, oven.getStatus());
        verify(heater, times(0)).start();
        verify(heater, times(1)).stop();
        checkEvents(MachineEvent.OVEN_READY, 2);
    }

    @Test
    public void testDegreesChange() {
        oven.start(200);
        for (int i = 0; i < 10; i++) {
            oven.observe(new OvenHeatChange(20));
        }
        checkEvents(MachineEvent.OVEN_DEGREES_CHANGE, 9);
        checkEvents(MachineEvent.OVEN_READY, 1);
        assertEquals(200, oven.getDegrees());
        verify(heater, times(1)).stop();
    }

    @Test
    public void testStop() {
        oven.start(200);
        oven.observe(new OvenHeatChange(50));
        assertEquals(50, oven.getDegrees());
        oven.stop();
        oven.stop();
        assertEquals(0, oven.getDegrees());
        assertEquals(OvenStatus.STOPPED, oven.getStatus());
        verify(heater, times(1)).stop();
        checkEvents(MachineEvent.OVEN_DEGREES_CHANGE, 1);
    }

    @Test
    public void testPause() {
        oven.start(200);
        oven.pause();
        oven.pause();
        assertEquals(OvenStatus.PAUSED, oven.getStatus());
        verify(heater, times(1)).pause();
    }

    private void initMocks() {
        doNothing().when(heater).start();
        doNothing().when(heater).stop();
        doNothing().when(heater).pause();
    }

}
