package com.misho.biscuit.biscuitmachine.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.misho.biscuit.biscuitmachine.components.motor.IMotor;
import com.misho.biscuit.biscuitmachine.components.oven.IOven;
import com.misho.biscuit.biscuitmachine.components.stamper.IStamper;

public class BiscuitMachineStopSequenceTest {
    @Mock
    private IOven oven;
    @Mock
    private IMotor motor;
    @Mock
    private IStamper stamper;
    @Mock
    private BiscuitMachineStopSequenceCallback callback;

    @InjectMocks
    private BiscuitMachineStopSequence sequence;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSequenceOrder() {
        var order = Mockito.inOrder(oven, motor, stamper, callback);
        sequence.execute();
        order.verify(motor).stop();
        order.verify(stamper).stop();
        order.verify(oven).stop();
        order.verify(callback).callBack();
    }
}
