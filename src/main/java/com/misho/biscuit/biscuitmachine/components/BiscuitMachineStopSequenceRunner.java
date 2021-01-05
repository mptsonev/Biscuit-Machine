package com.misho.biscuit.biscuitmachine.components;

import com.misho.biscuit.biscuitmachine.common.Constants;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BiscuitMachineStopSequenceRunner implements Runnable {
    private BiscuitMachineSequence stopSequence;
    private BiscuitStopCondition waitingCondition;
    private BiscuitStopCondition stopCondition;

    @Override
    public void run() {
        while (waitingCondition.isMet()) {
            try {
                Thread.sleep(Constants.MOTOR_RUNNER_SPEED / 2);
            } catch (InterruptedException e) {
                stopSequence.execute();
            }
        }
        if (stopCondition.isMet()) {
            stopSequence.execute();
        }

    }

}
