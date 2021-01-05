package com.misho.biscuit.biscuitmachine.web;

import javax.annotation.PostConstruct;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.misho.biscuit.biscuitmachine.components.IBiscuitMachine;
import com.misho.biscuit.biscuitmachine.config.CustomSpringConfigurator;

@ServerEndpoint(value = "/machine", configurator = CustomSpringConfigurator.class, encoders = WebsocketStateEncoder.class)
@Component
public class MachineWebsocketController {
    @Autowired
    private IBiscuitMachine biscuitMachine;

    @OnOpen
    public void onOpen(Session session) {
        var observer = new WebsocketMachineStateObserver(session);
        biscuitMachine.subscribe(observer);
        try {
            session.getAsyncRemote().sendObject(biscuitMachine.getState()).get();
        } catch (Exception e) {
            // TODO: Log error
        }
    }

    @OnMessage
    public void onMessage(String txt, Session session) {
        if (txt.equalsIgnoreCase("START")) {
            biscuitMachine.start();
        } else if (txt.equalsIgnoreCase("STOP")) {
            biscuitMachine.stop();
        } else if (txt.equalsIgnoreCase("PAUSE")) {
            biscuitMachine.pause();
        }
    }

    @PostConstruct
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

}
