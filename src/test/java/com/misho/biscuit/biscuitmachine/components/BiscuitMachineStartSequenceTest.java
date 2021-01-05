package com.misho.biscuit.biscuitmachine.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.misho.biscuit.biscuitmachine.components.extruder.IExtruder;
import com.misho.biscuit.biscuitmachine.components.motor.IMotor;
import com.misho.biscuit.biscuitmachine.components.stamper.IStamper;

public class BiscuitMachineStartSequenceTest {

    @Mock
    private IMotor motor;
    @Mock
    private IExtruder extruder;
    @Mock
    private IStamper stamper;

    @InjectMocks
    private BiscuitMachineStartSequence sequence;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSequenceOrder() {
        var order = Mockito.inOrder(motor, extruder, stamper);
        sequence.execute();
        order.verify(motor).start();
        order.verify(extruder).start();
        order.verify(stamper).start();
    }
}
