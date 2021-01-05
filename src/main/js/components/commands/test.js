import React from 'react';
import { fireEvent, render, screen } from '@testing-library/react';
import CommandPanel from './CommandPanel';
import '@testing-library/jest-dom';

describe('Renders Commands', () => {
  const renderAndClick = (expectedInstruction, testId) => {
    let instructions = [];
    render(
      <CommandPanel
        instructionCallback={instruction => {
          instructions.push(instruction);
        }}
      />
    );
    const element = screen.getByTestId(testId);
    expect(element).toHaveClass('button');
    fireEvent.click(element);
    expect(instructions[0]).toEqual(expectedInstruction);
    expect(element).toBeTruthy();
  };

  it('Sends Start instruction', () => {
    renderAndClick('Start', 'start-button');
  });

  it('Sends Stop instruction', () => {
    renderAndClick('Stop', 'stop-button');
  });

  it('Sends Pause instruction', () => {
    renderAndClick('Pause', 'pause-button');
  });
});
