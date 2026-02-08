package com.alessandro.caracciolo.catchit.Observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public abstract class Subject implements Runnable {

    private List<Observer> observers;
    private final Object MUTEX = new Object();
    protected boolean isAlive;

    public Subject() {
        this((Observer) null);
    }

    public Subject(Observer obs) {
        this(new Vector<Observer>());
        if (obs != null)
            this.observers.add(obs);
    }

    public Subject(List<Observer> list) {
        this.observers = list;
        this.isAlive = false;
    }

    public void attach(Observer obs) {
        synchronized (MUTEX) {
            this.observers.add(obs);
        }
    }

    public void detach(Observer obs) {
        synchronized (MUTEX) {
            this.observers.remove(obs);
        }
    }

    protected void notifyObservers() {
        List<Observer> observersLocal = null;

        // synchronization is used to make sure any observer registered after message is
        // received is not notified
        synchronized (MUTEX) {
            if (this.isThereAnythingToNotify())
                observersLocal = new ArrayList<Observer>(this.observers);

            if (observersLocal != null) {
                Iterator<Observer> i = observersLocal.iterator();
                while (i.hasNext()) {
                    Observer obs = i.next();
//System.out.println("Updating Observer from the Subject");
                    obs.update();
                }
            }
        }
    }

    @Override
    public void run() {
        this.isAlive = true;
        while (this.isAlive) {
            this.doSomething();
            this.notifyObservers();
        }
    }

    protected abstract boolean isThereAnythingToNotify();

    protected abstract void doSomething();
}
