package com;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class producer implements Runnable {

    Thread queuThread = new Thread();
    private int interarrivalTime;
    Long productsNum;
    Queue<Product> queue = new LinkedList<>();
    Queue<Product> tempQueue = new LinkedList<>();
    String queueID;

    public Queue<Product> getQueue() {
        return queue;
    }

    public producer(Long productsNum, Queue<Product> products, String ID) {
        queuThread.start();
        this.productsNum = productsNum;
        tempQueue = products;
        queueID = ID;
    }

    @Override
    public void run() {
        if (queueID.equals("Q1")) {
            Random rand = new Random();
            for (Long i = 1l; i <= productsNum; i++) {
                interarrivalTime = rand.nextInt(3000);
                synchronized (queuThread) {
                    try {
                        queuThread.sleep(interarrivalTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    queue.add(tempQueue.peek());
                    tempQueue.remove();
                    queuThread.notify();
                }
            }
        }
        else{
            
        }

    }
}
