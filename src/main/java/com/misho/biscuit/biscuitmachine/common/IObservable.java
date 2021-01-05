package com.misho.biscuit.biscuitmachine.common;

public interface IObservable<T> {

    public void notify(T message);

    public void subscribe(Observer<T> observer);

}
