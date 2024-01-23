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
    HashMap<String, Object> result;


    public producer(int productsNum, Queue<Product> queueElement ,Queue<Product> products, String ID, HashMap<String, Object> result) {
        this.productsNum = productsNum;
        tempQueue = products;
        queueID = ID;
        this.result = result;
        this.queue = queueElement;
        queuThread.setName(ID);
        queuThread.start();
    }

    public void updateResult(String key, Object value) {
        for (String k : result.keySet()) {
            if (k.equals(key)) {
                result.put(key, value);
            }
        }
    }

    @Override
    public void run() {
        for (Long i = 1l; i <= productsNum; i++) {
            Random rand = new Random();
            interarrivalTime = rand.nextInt(3000);
            try {
                queuThread.sleep(interarrivalTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("products num:" + i);
            synchronized (queue) {
                queue.add(tempQueue.peek());
                tempQueue.remove();
                updateResult(queueID, queue.size());
                queue.notify();
            }
        }
    }
}
