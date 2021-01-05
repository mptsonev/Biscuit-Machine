import React from 'react';
import PropTypes from 'prop-types';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faCookie,
  faStroopwafel,
  faCircle,
} from '@fortawesome/free-solid-svg-icons';

const Cookie = ({ status, title }) => {
  const getCookieComponent = () => {
    if (status === 'CREATED') {
      return faCookie;
    }
    if (status === 'STAMPED') {
      return faStroopwafel;
    }
    return faCircle;
  };

  return (
    <FontAwesomeIcon
      icon={getCookieComponent(status)}
      style={{ fontSize: '5em' }}
      title="Title"
    />
  );
};

Cookie.propTypes = {
  status: PropTypes.oneOf(['CREATED', 'STAMPED', 'NONE']).isRequired,
  title: PropTypes.string,
};

Cookie.defaultProps = {
  title: '',
};

export default Cookie;
