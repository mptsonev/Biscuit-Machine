package com.misho.biscuit.biscuitmachine.components.motor;

import com.misho.biscuit.biscuitmachine.common.Constants;

public class MotorRunner extends Thread {

    private MotorRunnerWorker worker;

    public MotorRunner(MotorRunnerWorker worker) {
        this.worker = worker;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Thread.sleep(Constants.MOTOR_RUNNER_SPEED);
                worker.move();
            } catch (InterruptedException e) {
                interrupt();
                // Motor Runner interrupted
            }
        }
    }

}
