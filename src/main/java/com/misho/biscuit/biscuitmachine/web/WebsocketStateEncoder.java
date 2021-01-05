package com.misho.biscuit.biscuitmachine.web;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;
import com.misho.biscuit.biscuitmachine.components.BiscuitMachineState;

public class WebsocketStateEncoder implements Encoder.Text<BiscuitMachineState> {

    private static Gson gson = new Gson();

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }

    @Override
    public void destroy() {
        // Close resources
    }

    @Override
    public String encode(BiscuitMachineState object) throws EncodeException {
        return gson.toJson(object);
    }

}
