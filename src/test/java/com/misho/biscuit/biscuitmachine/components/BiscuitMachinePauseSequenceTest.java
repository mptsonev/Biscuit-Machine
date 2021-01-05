package com.misho.biscuit.biscuitmachine.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.misho.biscuit.biscuitmachine.components.extruder.IExtruder;
import com.misho.biscuit.biscuitmachine.components.motor.IMotor;
import com.misho.biscuit.biscuitmachine.components.oven.IOven;
import com.misho.biscuit.biscuitmachine.components.stamper.IStamper;

public class BiscuitMachinePauseSequenceTest {

    @Mock
    private IMotor motor;
    @Mock
    private IOven oven;
    @Mock
    private IExtruder extruder;
    @Mock
    private IStamper stamper;

    @InjectMocks
    private BiscuitMachinePauseSequence sequence;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSequenceOrder() {
        var order = Mockito.inOrder(motor, oven, extruder, stamper);
        sequence.execute();
        order.verify(motor).stop();
        order.verify(oven).pause();
        order.verify(extruder).stop();
        order.verify(stamper).stop();
    }
}
