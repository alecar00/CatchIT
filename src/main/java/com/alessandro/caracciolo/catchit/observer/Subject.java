package com.alessandro.caracciolo.catchit.observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

public abstract class Subject implements Runnable {

    Logger logger =  Logger.getLogger(Subject.class.getName());

    private List<Observer> observers;
    private final Object mutex = new Object();
    protected boolean isAlive;

    protected Subject() {
        this((Observer) null);
    }

    protected Subject(Observer obs) {
        this(new Vector<Observer>());
        if (obs != null)
            this.observers.add(obs);
    }

    protected Subject(List<Observer> list) {
        this.observers = list;
        this.isAlive = false;
    }

    public void attach(Observer obs) {
        synchronized (mutex) {
            this.observers.add(obs);
        }
    }

    public void detach(Observer obs) {
        synchronized (mutex) {
            this.observers.remove(obs);
        }
    }

    protected void notifyObservers() {
        List<Observer> observersLocal = null;

        // synchronization is used to make sure any observer registered after message is
        // received is not notified
        synchronized (mutex) {
            if (this.isThereAnythingToNotify())
                observersLocal = new ArrayList<Observer>(this.observers);

            if (observersLocal != null) {
                Iterator<Observer> i = observersLocal.iterator();
                while (i.hasNext()) {
                    Observer obs = i.next();
                    logger.info("Updating Observer from the Subject");
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
