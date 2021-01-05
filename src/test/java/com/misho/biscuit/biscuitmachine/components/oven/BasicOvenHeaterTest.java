package com.misho.biscuit.biscuitmachine.components.oven;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.misho.biscuit.biscuitmachine.TestObserver;
import com.misho.biscuit.biscuitmachine.common.Constants;

public class BasicOvenHeaterTest extends TestObserver<OvenHeatChange> {

    private IOvenHeater heater;

    @BeforeEach
    public void setUp() {
        heater = new BasicOvenHeater();
        heater.subscribe(this);
    }

    @Test
    public void testStart() {
        heater.start();
        try {
            Thread.sleep(Constants.MOTOR_RUNNER_SPEED + 100);
            heater.stop();
            checkEvents(new OvenHeatChange(40), 2);
        } catch (Exception e) {
            fail("Unexpected exception occured " + e);
        }
    }

    @Test
    public void testPause() {
        heater.start();
        try {
            Thread.sleep(Constants.MOTOR_RUNNER_SPEED / 2 + 100);
            heater.pause();
            Thread.sleep(Constants.MOTOR_RUNNER_SPEED / 2 + 100);
            heater.start();
            Thread.sleep(Constants.MOTOR_RUNNER_SPEED / 2 + 100);
            checkEvents(new OvenHeatChange(40), 2);
        } catch (Exception e) {
            fail("Unexpected exception occured " + e);
        }
    }

}
