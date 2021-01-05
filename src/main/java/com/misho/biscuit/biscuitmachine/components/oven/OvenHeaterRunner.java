package com.misho.biscuit.biscuitmachine.components.oven;

import com.misho.biscuit.biscuitmachine.common.Constants;

public class OvenHeaterRunner extends Thread {

    private OvenHeaterRunnerWorker worker;
    private boolean paused = false;

    public OvenHeaterRunner(OvenHeaterRunnerWorker worker) {
        this.worker = worker;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Thread.sleep(Constants.MOTOR_RUNNER_SPEED / 2);
                if (paused) {
                    continue;
                }
                worker.heat();
            } catch (InterruptedException e) {
                interrupt();
                // Heater stopped
            }
        }
    }

    public void pause() {
        this.paused = true;
    }

    public void unpause() {
        this.paused = false;
    }
}
