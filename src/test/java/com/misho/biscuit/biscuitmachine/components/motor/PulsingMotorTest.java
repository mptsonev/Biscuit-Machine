package com.misho.biscuit.biscuitmachine.components.motor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.misho.biscuit.biscuitmachine.TestObserver;
import com.misho.biscuit.biscuitmachine.common.Constants;

public class PulsingMotorTest extends TestObserver<MotorEvent> {
    private IMotor motor;

    @BeforeEach
    public void setUp() {
        this.motor = new PulsingMotor();
        motor.subscribe(this);
    }

    @AfterEach
    public void tearDown() {
        this.motor.stop();
        assertEquals(MotorStatus.STOPPED, motor.getStatus());
    }

    @Test
    public void testStart() {
        assertEquals(MotorStatus.STOPPED, motor.getStatus());
        motor.start();
        assertEquals(MotorStatus.RUNNING, motor.getStatus());
        try {
            Thread.sleep(Constants.MOTOR_RUNNER_SPEED * 2 + 100);
            checkEvents(MotorEvent.PULSE, 2);
        } catch (Exception e) {
            fail(e.toString());
        }
    }

}
