package com.example.concurrency;

public class Originator {
    private State state;

    public State getState() {
        return state;
    }

    public void setState() {
        this.state = new State();
    }

    public void getStateFromMemento(Memento memento){
        state = memento.getState();
    }

    public Memento saveStateToMemento(){
        return new Memento(state);
    }
}
