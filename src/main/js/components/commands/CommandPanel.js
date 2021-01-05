import React from 'react';
import PropTypes from 'prop-types';
import styles from './CommandPanel.css';

const CommandPanel = ({ instructionCallback }) => {
  return (
    <div>
      <input
        data-testid="start-button"
        className={styles.button}
        type="button"
        value="START"
        onClick={() => instructionCallback('Start')}
      />
      <input
        data-testid="pause-button"
        className={styles.button}
        type="button"
        value="PAUSE"
        onClick={() => instructionCallback('Pause')}
      />
      <input
        data-testid="stop-button"
        className={styles.button}
        type="button"
        value="STOP"
        onClick={() => instructionCallback('Stop')}
      />
    </div>
  );
};

CommandPanel.propTypes = {
  instructionCallback: PropTypes.func.isRequired,
};

export default CommandPanel;
