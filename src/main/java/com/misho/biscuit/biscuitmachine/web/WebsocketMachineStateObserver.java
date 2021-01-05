package com.misho.biscuit.biscuitmachine.web;

import java.util.concurrent.ExecutionException;

import javax.websocket.Session;

import com.misho.biscuit.biscuitmachine.common.Observer;
import com.misho.biscuit.biscuitmachine.components.BiscuitMachineState;

public class WebsocketMachineStateObserver implements Observer<BiscuitMachineState> {
    private Session session;

    public WebsocketMachineStateObserver(Session session) {
        this.session = session;
    }

    @Override
    public void observe(BiscuitMachineState event) {
        if (session.isOpen()) {
            try {
                synchronized (session) {
                    session.getAsyncRemote().sendObject(event).get();
                }
            } catch (InterruptedException | ExecutionException e) {
                // TODO: Log this error
            }
        }
    }

}
