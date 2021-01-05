import React from 'react';
import BiscuitBasket from './basket/BiscuitBasket';
import Cookie from './cookie/Cookie';
import Motor from './motor/Motor';
import Oven from './oven/Oven';
import CommandPanel from './commands/CommandPanel';

const BiscuitMachine = ({ machineState, instructionCallback }) => {
  return (
    <div>
      <h1>Biscuit Machine</h1>
      <h2>Status: {machineState.machineStatus}</h2>
      <CommandPanel instructionCallback={instructionCallback} />
      <Oven
        degrees={machineState.ovenDegrees}
        status={machineState.ovenStatus}
      />
      <Motor status={machineState.motorStatus} />
      <BiscuitBasket count={machineState.readyBiscuits} />
      <div style={{ padding: '1em' }}>
        {machineState.conveyorState.map((cookieStatus, index) => {
          return <Cookie key={index} status={cookieStatus} />;
        })}
      </div>
    </div>
  );
};

export default BiscuitMachine;
