package com.alessandro.caracciolo.catchit.Observer;

public abstract class Observer implements Runnable{

    protected long timeOut;
    protected boolean isAlive;

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
                e.printStackTrace();
            }
        }
    }

    public void stopObservation(){
        this.isAlive = false;
    }

    protected void notifySubjectStatus(String message){
        System.out.println("---> " + message);
    }

    public abstract void update();
}

