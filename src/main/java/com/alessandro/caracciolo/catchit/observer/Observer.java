package com.alessandro.caracciolo.catchit.observer;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Observer implements Runnable{

    protected long timeOut;
    protected boolean isAlive;
    private static final Logger logger = Logger.getLogger(Observer.class.getName());

    protected Observer(int timeOut){
        this.timeOut = timeOut;
        this.isAlive = false;
    }

    @Override
    public void run() {
        this.isAlive = true;
        while (this.isAlive){
            this.update();
            try {
                Thread.sleep(this.timeOut);
            } catch (InterruptedException e) {
                logger.log(Level.WARNING, "Thread dell'Observer interrotto durante l'attesa.", e);
                Thread.currentThread().interrupt();
                this.stopObservation();
            }
        }
    }

    public void stopObservation(){
        this.isAlive = false;
    }

    protected void notifySubjectStatus(String message){
        //not implemented
    }

    public abstract void update();
}

