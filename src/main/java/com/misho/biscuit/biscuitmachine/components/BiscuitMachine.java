package com.misho.biscuit.biscuitmachine.components;

import com.misho.biscuit.biscuitmachine.common.Constants;
import com.misho.biscuit.biscuitmachine.common.MachineEvent;
import com.misho.biscuit.biscuitmachine.common.Observable;
import com.misho.biscuit.biscuitmachine.common.Observer;
import com.misho.biscuit.biscuitmachine.components.conveyor.IConveyor;
import com.misho.biscuit.biscuitmachine.components.extruder.IExtruder;
import com.misho.biscuit.biscuitmachine.components.motor.IMotor;
import com.misho.biscuit.biscuitmachine.components.oven.IOven;
import com.misho.biscuit.biscuitmachine.components.stamper.IStamper;

public class BiscuitMachine extends Observable<BiscuitMachineState> implements IBiscuitMachine, Observer<MachineEvent> {

    private IMotor motor;
    private IOven oven;
    private IConveyor conveyor;
    private IExtruder extruder;
    private IStamper stamper;
    private BiscuitMachineSequence startSequence;
    private BiscuitMachineSequence stopSequence;
    private BiscuitMachineSequence pauseSequence;

    private volatile BiscuitMachineStatus status = BiscuitMachineStatus.STOPPED;
    int readyBiscuits = 0;

    private BiscuitMachine(IMotor motor, IOven oven, IConveyor conveyor, IExtruder extruder, IStamper stamper) {
        this.motor = motor;
        this.oven = oven;
        this.conveyor = conveyor;
        this.extruder = extruder;
        this.stamper = stamper;
    }

    @Override
    public void start() {
        if (status == BiscuitMachineStatus.RUNNING) {
            return;
        }
        status = BiscuitMachineStatus.RUNNING;
        // Start the heater and wait till ready
        oven.start(Constants.OVEN_REQUIRED_DEGREES);
        notify(getState());
    }

    @Override
    public void stop() {
        if (status == BiscuitMachineStatus.STOPPED || status == BiscuitMachineStatus.STOPPING) {
            return;
        }
        if (status == BiscuitMachineStatus.PAUSED) {
            // Resume work for the stop sequence
            stamper.start();
            motor.start();
        }
        status = BiscuitMachineStatus.STOPPING;
        extruder.stop();
        oven.pause();
        submitStopSequence();
        notify(getState());
    }

    @Override
    public void pause() {
        if (status == BiscuitMachineStatus.PAUSED) {
            return;
        }
        status = BiscuitMachineStatus.PAUSED;
        pauseSequence.execute();
        notify(getState());
    }

    @Override
    public void observe(MachineEvent event) {
        switch (event) {
        case OVEN_READY:
            startSequence.execute();
            break;
        case BISCUIT_READY:
            readyBiscuits++;
            break;
        default: // Just send state notifications
        }
        notify(getState());
    }

    @Override
    public BiscuitMachineStatus getStatus() {
        return status;
    }

    @Override
    public BiscuitMachineState getState() {
        return new BiscuitMachineState(readyBiscuits, motor.getStatus(), oven.getStatus(), oven.getDegrees(), status,
                conveyor.getState());
    }

    private void submitStopSequence() {
        new Thread(getStopSequenceRunner()).start();
    }

    private BiscuitMachineStopSequenceRunner getStopSequenceRunner() {
        // Wait for the conveyor to move the already extruded items
        BiscuitStopCondition waitingCondition = () -> !conveyor.isEmpty() && status == BiscuitMachineStatus.STOPPING;
        BiscuitStopCondition stopCondition = () -> status == BiscuitMachineStatus.STOPPING;
        return new BiscuitMachineStopSequenceRunner(stopSequence, waitingCondition, stopCondition);
    }

    public static BiscuitMachineBuilder builder() {
        return new BiscuitMachineBuilder();
    }

    public static class BiscuitMachineBuilder {
        private IMotor motor;
        private IOven oven;
        private IConveyor conveyor;
        private IExtruder extruder;
        private IStamper stamper;

        public BiscuitMachineBuilder motor(IMotor motor) {
            this.motor = motor;
            return this;
        }

        public BiscuitMachineBuilder oven(IOven oven) {
            this.oven = oven;
            return this;
        }

        public BiscuitMachineBuilder conveyor(IConveyor conveyor) {
            this.conveyor = conveyor;
            return this;
        }

        public BiscuitMachineBuilder extruder(IExtruder extruder) {
            this.extruder = extruder;
            return this;
        }

        public BiscuitMachineBuilder stamper(IStamper stamper) {
            this.stamper = stamper;
            return this;
        }

        public BiscuitMachine build() {
            motor.subscribe(conveyor);
            motor.subscribe(extruder);
            motor.subscribe(stamper);
            var machine = new BiscuitMachine(motor, oven, conveyor, extruder, stamper);
            oven.subscribe(machine);
            conveyor.subscribe(machine);
            BiscuitMachineStopSequenceCallback stopCallback = () -> {
                machine.status = BiscuitMachineStatus.STOPPED;
                machine.notify(machine.getState());
            };
            machine.stopSequence = new BiscuitMachineStopSequence(oven, motor, stamper, stopCallback);
            machine.pauseSequence = new BiscuitMachinePauseSequence(motor, oven, extruder, stamper);
            machine.startSequence = new BiscuitMachineStartSequence(motor, extruder, stamper);
            return machine;
        }

    }

}
