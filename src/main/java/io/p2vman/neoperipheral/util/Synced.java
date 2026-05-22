package io.p2vman.neoperipheral.util;

public class Synced<T> {
    private T value;

    public Synced(T init){
        value = init;
    }

    public synchronized void set(T value){
        this.value = value;
    }

    public synchronized T getThenSet(T value){
        T org = this.value;
        set(value);
        return org;
    }

    public T get(){
        return value;
    }

}
