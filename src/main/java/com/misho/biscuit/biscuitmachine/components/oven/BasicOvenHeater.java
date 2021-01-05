package com.misho.biscuit.biscuitmachine.components.oven;

import com.misho.biscuit.biscuitmachine.common.Observable;

public class BasicOvenHeater extends Observable<OvenHeatChange> implements IOvenHeater {
    private OvenHeaterRunner runner;

    @Override
    public void start() {
        if (runner != null && runner.isAlive()) {
            runner.unpause();
            return;
        }
        runner = new OvenHeaterRunner(() -> notify(new OvenHeatChange(40)));
        runner.start();
    }

    @Override
    public void stop() {
        runner.interrupt();
    }

    @Override
    public void pause() {
        if (runner != null) {
            runner.pause();
        }
    }

}
