import React from 'react';
import { render, screen } from '@testing-library/react';
import Basket from './BiscuitBasket';

describe('Renders Basket', () => {
  it('Renders a Basket with no cookies', () => {
    render(<Basket count={0} />);
    const element = screen.getByTestId('baked-biscuits-text');
    expect(element.textContent).toEqual('Baked Biscuits: 0');
  });
  it('Renders a Basket with cookies', () => {
    render(<Basket count={50} />);
    const element = screen.getByTestId('baked-biscuits-text');
    expect(element.textContent).toEqual('Baked Biscuits: 50');
  });
});
