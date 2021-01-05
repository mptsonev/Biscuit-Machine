import React from 'react';
import { render, screen } from '@testing-library/react';
import Oven from './Oven';

describe('Renders Oven', () => {
  it('Renders a Stopped oven', () => {
    render(<Oven degrees={0} status="STOPPED" />);
    const degrees = screen.getByTestId('oven-degrees');
    const status = screen.getByTestId('oven-status');
    expect(degrees.textContent).toEqual('Oven Degrees: 0');
    expect(status.textContent).toEqual('Status: STOPPED');
  });

  it('Renders a Running oven', () => {
    render(<Oven degrees={240} status="RUNNING" />);
    const degrees = screen.getByTestId('oven-degrees');
    const status = screen.getByTestId('oven-status');
    expect(degrees.textContent).toEqual('Oven Degrees: 240');
    expect(status.textContent).toEqual('Status: RUNNING');
  });
});
