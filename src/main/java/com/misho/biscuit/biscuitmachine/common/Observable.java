package com.misho.biscuit.biscuitmachine.common;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Observable<T> implements IObservable<T> {

    private Collection<Observer<T>> observers = new ConcurrentLinkedQueue<>();

    public void notify(T message) {
        observers.forEach(observer -> observer.observe(message));
    }

    public void subscribe(Observer<T> observer) {
        observers.add(observer);
    }

}
