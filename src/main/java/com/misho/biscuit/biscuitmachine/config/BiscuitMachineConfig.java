package com.misho.biscuit.biscuitmachine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.misho.biscuit.biscuitmachine.components.BiscuitMachine;
import com.misho.biscuit.biscuitmachine.components.conveyor.PulsingConveyor;
import com.misho.biscuit.biscuitmachine.components.extruder.PulsingExtruder;
import com.misho.biscuit.biscuitmachine.components.motor.PulsingMotor;
import com.misho.biscuit.biscuitmachine.components.oven.BasicOvenHeater;
import com.misho.biscuit.biscuitmachine.components.oven.Oven;
import com.misho.biscuit.biscuitmachine.components.stamper.PulsingStamper;

@Configuration
public class BiscuitMachineConfig {

    @Bean
    public BiscuitMachine biscuitMachine() {
        var motor = new PulsingMotor();
        var conveyor = new PulsingConveyor();
        var oven = Oven.builder().heater(new BasicOvenHeater()).build();
        var extruder = new PulsingExtruder(conveyor);
        var stamper = new PulsingStamper(conveyor);
        var machine = BiscuitMachine.builder().motor(motor).oven(oven).conveyor(conveyor).extruder(extruder)
                .stamper(stamper).build();
        return machine;
    }

}
