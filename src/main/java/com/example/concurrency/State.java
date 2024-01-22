package com.example.concurrency;
import java.util.ArrayList;
import java.util.Queue;

public class State {
    private ArrayList<Queue> queues;
    private ArrayList<Machine> Machines;

    public State() {
        ConcurrencyService shot =new ConcurrencyService();
        this.queues = (ArrayList<Queue>) shot.queues.clone();
        this.Machines = (ArrayList<Machine>) shot.Machines.clone();
    }

    public ArrayList<Queue> getQueues() {
        return queues;
    }

    public ArrayList<Machine> getMachines() {
        return Machines;
    }

    
    
    
}
// to get the states for re-simulate
// int  i = 0;
/* while(!careTaker.isEmpty()){
    originator.getStateFromMemento(careTaker.get(i));
    state = originator.getState();
    i++;
}
 */

// to save state in each step
/* Originator originator = new Originator();
   CareTaker careTaker = new CareTaker();
   originator.setState();
   careTaker.add(originator.saveStateToMemento());
 */