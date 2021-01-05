import React, { useEffect, useState } from 'react';
import ReactDOM from 'react-dom';
import BiscuitMachine from './components/BiscuitMachine';

const App = () => {
  const [machineState, setMachineState] = useState({
    machineStatus: 'STOPPED',
    ovenDegrees: 0,
    ovenStatus: 'STOPPED',
    motorStatus: 'STOPPED',
    readyBiscuits: 0,
    conveyorState: [],
  });
  const wsProtocol = location.protocol === 'http:' ? 'ws://' : 'wss://';
  const wsHost = location.host;
  const wsEndpoint = '/machine';
  const wsUrl = wsProtocol + wsHost + wsEndpoint;
  const [webSocket] = useState(new WebSocket(wsUrl));

  useEffect(() => {
    webSocket.onmessage = event => {
      setMachineState(JSON.parse(event.data));
    };
  }, []);

  return (
    <BiscuitMachine
      machineState={machineState}
      instructionCallback={instruction => webSocket.send(instruction)}
    />
  );
};

ReactDOM.render(<App />, document.getElementById('react'));
