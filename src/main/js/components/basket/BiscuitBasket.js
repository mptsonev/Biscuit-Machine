import { faShoppingBasket } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import PropTypes from 'prop-types';
import React from 'react';
import styles from './BiscuitBasket.css';

const BiscuitBasket = ({ count }) => {
  return (
    <div className={styles.shoppingBasketContainer}>
      <p data-testid="baked-biscuits-text" className={styles.text}>
        Baked Biscuits: {count}
      </p>
      <FontAwesomeIcon
        icon={faShoppingBasket}
        className={styles.shoppingBasket}
      />
    </div>
  );
};

BiscuitBasket.propTypes = {
  count: PropTypes.number.isRequired,
};

export default BiscuitBasket;
