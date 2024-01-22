package com.example.demo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class producer implements Runnable {

    Thread queuThread = new Thread(this);
    private int interarrivalTime;
    int productsNum;
    Queue<Product> queue = new LinkedList<>();
    Queue<Product> tempQueue = new LinkedList<>();
    String queueID;
    HashMap<String, String[]> connection;

    public Queue<Product> getQueue() {
        return queue;
    }

    public producer(int productsNum, Queue<Product> products, String ID, HashMap<String, String[]> conncetion) {
        this.productsNum = productsNum;
        tempQueue = products;
        queueID = ID;
        this.connection = conncetion;
        queuThread.setName(ID);
        queuThread.start();
    }

    @Override
    public void run() {
        for (Long i = 1l; i <= productsNum; i++) {
            if (queueID.equals("Q1")) {
                Random rand = new Random();
                interarrivalTime = rand.nextInt(3000);
                try {
                    queuThread.sleep(interarrivalTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("products num:"+i);
                synchronized(queue) {
                    queue.add(tempQueue.peek());
                    tempQueue.remove();
                    queue.notify();
                }
            } else {
                if (connection.get(queueID) == null)
                    ;
                else {
                    for (String machine : connection.get(queueID)) {
                        new Machine(getQueue(), productsNum, connection, machine);
                    }
                }
                synchronized(tempQueue) {
                    while(tempQueue.isEmpty()){
                        try {
                            tempQueue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.add(tempQueue.element());
                    System.out.println(queuThread.getName()+"    size: "+queue.size());
                    tempQueue.remove();
                    tempQueue.notify();
                }
            }
        }
    }
}
