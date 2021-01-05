package com.misho.biscuit.biscuitmachine.components.oven;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.misho.biscuit.biscuitmachine.common.Constants;

public class OvenHeaterRunnerTest {

    private int heatCount;

    @BeforeEach
    public void setUp() {
        this.heatCount = 0;
    }

    private void increment() {
        heatCount++;
    }

    @Test
    public void testRunner() {
        var runner = new OvenHeaterRunner(() -> increment());
        runner.unpause();
        runner.start();
        try {
            Thread.sleep(Constants.MOTOR_RUNNER_SPEED + 100);
            assertEquals(2, heatCount);
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void testInterrupt() {
        var runner = new OvenHeaterRunner(() -> increment());
        runner.unpause();
        runner.start();
        try {
            Thread.sleep(Constants.MOTOR_RUNNER_SPEED / 2 + 100);
            runner.interrupt();
            assertEquals(1, heatCount);
        } catch (Exception e) {
            fail(e.toString());
        }
    }
    

    @Test
    public void testPaused() {
        var runner = new OvenHeaterRunner(() -> increment());
        runner.start();
        runner.pause();
        try {
            Thread.sleep(Constants.MOTOR_RUNNER_SPEED + 100);
            runner.interrupt();
            assertEquals(0, heatCount);
        } catch (Exception e) {
            fail(e.toString());
        }
    }


}
