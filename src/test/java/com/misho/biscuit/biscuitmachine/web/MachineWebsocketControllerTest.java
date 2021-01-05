package com.misho.biscuit.biscuitmachine.web;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.Future;

import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.Session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.misho.biscuit.biscuitmachine.common.BiscuitState;
import com.misho.biscuit.biscuitmachine.components.BiscuitMachineState;
import com.misho.biscuit.biscuitmachine.components.BiscuitMachineStatus;
import com.misho.biscuit.biscuitmachine.components.IBiscuitMachine;
import com.misho.biscuit.biscuitmachine.components.motor.MotorStatus;
import com.misho.biscuit.biscuitmachine.components.oven.OvenStatus;

public class MachineWebsocketControllerTest {

    @InjectMocks
    private MachineWebsocketController controller;

    @Mock
    private IBiscuitMachine machine;
    @Mock
    private Async remote;
    @Mock
    private Future<Void> remoteAnswer;

    @Mock
    private Session session;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        doReturn(remoteAnswer).when(remote).sendObject(any());
        initMocks();
    }

    @Test
    public void testStart() {
        controller.onMessage("Start", session);
        verify(machine, times(1)).start();
    }

    @Test
    public void testStop() {
        controller.onMessage("Stop", session);
        verify(machine, times(1)).stop();
    }

    @Test
    public void testPause() {
        controller.onMessage("Pause", session);
        verify(machine, times(1)).pause();
    }

    @Test
    public void testOpen() {
        when(session.getAsyncRemote()).thenReturn(remote);
        controller.onOpen(session);
        verify(session, times(1)).getAsyncRemote();
        try {
            verify(remote, times(1)).sendObject(getTestState());
            verify(remoteAnswer, times(1)).get();
        } catch (Exception e) {
            fail("Exception occured");
        }
    }

    @Test
    public void testThrows() {
        when(session.getAsyncRemote()).thenReturn(remote);
        try {
            doThrow(new InterruptedException()).when(remoteAnswer).get();
            controller.onOpen(session);
        } catch (Exception e) {
            fail("Unexpected exception occured " + e);
        }
    }

    private void initMocks() {
        doNothing().when(machine).start();
        doNothing().when(machine).stop();
        doNothing().when(machine).pause();
        doReturn(getTestState()).when(machine).getState();

    }

    private BiscuitMachineState getTestState() {
        var conveyorState = new BiscuitState[6];
        return new BiscuitMachineState(1, MotorStatus.RUNNING, OvenStatus.RUNNING, 240, BiscuitMachineStatus.RUNNING,
                conveyorState);
    }
}
