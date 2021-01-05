import React from 'react';
import { render, screen } from '@testing-library/react';
import Cookie from './Cookie';

describe('Renders Cookies', () => {
  it('Renders a Cookie in Created state', () => {
    render(<Cookie status="CREATED" />);
    const element = screen.getByTitle('Title');
    expect(element).toBeTruthy();
  });
  it('Renders a Cookie in Created state', () => {
    render(<Cookie status="STAMPED" />);
    const element = screen.getByTitle('Title');
    expect(element).toBeTruthy();
  });
  it('Renders an empty Cookie', () => {
    render(<Cookie status="NONE" />);
    const element = screen.getByTitle('Title');
    expect(element).toBeTruthy();
  });
});
