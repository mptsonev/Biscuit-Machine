import React from 'react';
import PropTypes from 'prop-types';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCog } from '@fortawesome/free-solid-svg-icons';
import styles from './Motor.css';

const Motor = ({ status }) => {
  let firstGearClassNames = styles.firstGear;
  let middleGearClassNames = styles.middleGear;
  let lastGearClassNames = styles.lastGear;
  if (status === 'RUNNING') {
    firstGearClassNames += ' ' + styles.spin;
    middleGearClassNames += ' ' + styles.spinBack;
    lastGearClassNames += ' ' + styles.spin;
  }

  return (
    <>
      <FontAwesomeIcon
        data-testid="first-gear"
        icon={faCog}
        className={firstGearClassNames}
      />
      <FontAwesomeIcon
        data-testid="middle-gear"
        icon={faCog}
        className={middleGearClassNames}
      />
      <FontAwesomeIcon
        data-testid="last-gear"
        icon={faCog}
        className={lastGearClassNames}
      />
    </>
  );
};

Motor.propTypes = {
  status: PropTypes.oneOf(['RUNNING', 'STOPPED']).isRequired,
};

export default Motor;
