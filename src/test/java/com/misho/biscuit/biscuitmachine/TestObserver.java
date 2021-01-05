package com.misho.biscuit.biscuitmachine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;

import com.misho.biscuit.biscuitmachine.common.Observer;

public abstract class TestObserver<T> implements Observer<T> {
    private List<T> observedEvents = new LinkedList<T>();
    private int eventsChecked = 0;

    @Override
    public void observe(T event) {
        observedEvents.add(event);
    }

    protected void checkEvents(T eventType, int expectedOccurances) {
        int occurances = (int) observedEvents.stream().filter(event -> event.equals(eventType)).count();
        assertEquals(expectedOccurances, occurances);
        eventsChecked += expectedOccurances;
    }

    @AfterEach
    public void checkForUncheckedEvents() {
        assertEquals(eventsChecked, observedEvents.size());
    }
}
