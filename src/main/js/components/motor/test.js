import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import Motor from './Motor';

describe('Renders Motor', () => {
  it('Renders a Running Motor', () => {
    render(<Motor status="RUNNING" />);

    const firstGear = screen.getByTestId('first-gear');
    expect(firstGear).toHaveClass('firstGear');
    expect(firstGear).toHaveClass('spin');

    const middleGear = screen.getByTestId('middle-gear');
    expect(middleGear).toHaveClass('middleGear');
    expect(middleGear).toHaveClass('spinBack');

    const lastGear = screen.getByTestId('last-gear');
    expect(lastGear).toHaveClass('lastGear');
    expect(lastGear).toHaveClass('spin');
  });

  it('Renders a Stopped Motor', () => {
    render(<Motor status="STOPPED" />);

    const firstGear = screen.getByTestId('first-gear');
    expect(firstGear).toHaveClass('firstGear');
    expect(firstGear).not.toHaveClass('spin');

    const middleGear = screen.getByTestId('middle-gear');
    expect(middleGear).toHaveClass('middleGear');
    expect(middleGear).not.toHaveClass('spinBack');

    const lastGear = screen.getByTestId('last-gear');
    expect(lastGear).toHaveClass('lastGear');
    expect(lastGear).not.toHaveClass('spin');
  });
});
