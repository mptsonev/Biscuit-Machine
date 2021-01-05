package com.misho.biscuit.biscuitmachine.web;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.Future;

import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.Session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.misho.biscuit.biscuitmachine.common.BiscuitState;
import com.misho.biscuit.biscuitmachine.components.BiscuitMachineState;
import com.misho.biscuit.biscuitmachine.components.BiscuitMachineStatus;
import com.misho.biscuit.biscuitmachine.components.motor.MotorStatus;
import com.misho.biscuit.biscuitmachine.components.oven.OvenStatus;

public class WebsocketMachineStateObserverTest {

    @Mock
    private Session session;
    @Mock
    private Async remote;
    @Mock
    private Future<Void> remoteAnswer;

    private WebsocketMachineStateObserver observer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        doReturn(remote).when(session).getAsyncRemote();
        doReturn(remoteAnswer).when(remote).sendObject(any());
        this.observer = new WebsocketMachineStateObserver(session);
    }

    @Test
    public void testObserve() {
        doReturn(true).when(session).isOpen();
        observer.observe(getTestState());
        verify(session, times(1)).getAsyncRemote();
        try {
            verify(remote, times(1)).sendObject(getTestState());
            verify(remoteAnswer, times(1)).get();
        } catch (Exception e) {
            fail("Unexpected exception occured " + e);
        }
    }

    @Test
    public void testClosedSession() {
        doReturn(false).when(session).isOpen();
        observer.observe(getTestState());
        verify(session, times(0)).getAsyncRemote();
        try {
            verify(remote, times(0)).sendObject(any());
        } catch (Exception e) {
            fail("Unexpected exception occured " + e);
        }
    }

    @Test
    public void testError() {
        doReturn(true).when(session).isOpen();
        try {
            doThrow(new InterruptedException()).when(remoteAnswer).get();
            observer.observe(getTestState());
            verify(session, times(1)).getAsyncRemote();
        } catch (Exception e) {
            fail("Unexpected exception occured " + e);
        }
    }

    private BiscuitMachineState getTestState() {
        var conveyorState = new BiscuitState[6];
        return new BiscuitMachineState(1, MotorStatus.RUNNING, OvenStatus.RUNNING, 240, BiscuitMachineStatus.RUNNING,
                conveyorState);
    }

}
