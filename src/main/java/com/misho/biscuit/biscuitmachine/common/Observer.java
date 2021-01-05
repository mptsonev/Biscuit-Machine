package com.misho.biscuit.biscuitmachine.common;

public interface Observer<T> {
    
    public void observe(T event);

}
