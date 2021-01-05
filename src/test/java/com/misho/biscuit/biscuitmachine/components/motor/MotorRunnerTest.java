package com.misho.biscuit.biscuitmachine.components.motor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.misho.biscuit.biscuitmachine.common.Constants;

public class MotorRunnerTest {

    private int moveCount;

    @BeforeEach
    public void setUp() {
        this.moveCount = 0;
    }

    private void increment() {
        moveCount++;
    }

    @Test
    public void testRunner() {
        var runner = new MotorRunner(() -> increment());
        runner.start();
        try {
            Thread.sleep(Constants.MOTOR_RUNNER_SPEED * 2 + 100);
            assertEquals(2, moveCount);
        } catch (Exception e) {
            fail(e.toString());
        }
    }
    
    @Test
    public void testInterrupt() {
        var runner = new MotorRunner(() -> increment());
        runner.start();
        try {
            Thread.sleep(Constants.MOTOR_RUNNER_SPEED + 100);
            runner.interrupt();
            assertEquals(1, moveCount);
        } catch (Exception e) {
            fail(e.toString());
        }
    }

}
