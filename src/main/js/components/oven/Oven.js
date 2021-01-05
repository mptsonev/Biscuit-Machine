import { faTemperatureHigh } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import PropTypes from 'prop-types';
import React from 'react';
import styles from './Oven.css';

const Oven = ({ degrees, status }) => {
  return (
    <div className={styles.container}>
      <p data-testid="oven-degrees" className={styles.text}>
        Oven Degrees: {degrees}
      </p>
      <FontAwesomeIcon
        icon={faTemperatureHigh}
        className={styles.temperature}
      />
      <p data-testid="oven-status" className={styles.status}>
        Status: {status}
      </p>
    </div>
  );
};

Oven.propTypes = {
  degrees: PropTypes.number.isRequired,
  status: PropTypes.string.isRequired,
};

export default Oven;
