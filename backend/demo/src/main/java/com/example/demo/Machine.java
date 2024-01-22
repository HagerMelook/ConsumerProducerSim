package com.example.demo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import javax.swing.text.StyledEditorKit.BoldAction;

public class Machine implements Runnable {
    Thread thread = new Thread(this);
    private Long processingTime;
    Random rand = new Random();
    Queue<Product> eQueue = new LinkedList<>();
    Queue<Product> nextQueue = new LinkedList<>();
    HashMap<String, String[]> connection;
    String machineID;
    int productsNum;

    int queueNo;

    public Machine(Queue<Product> queue, int productsNum, HashMap<String, String[]> connection, String machineID) {
        this.connection = connection;
        this.machineID = machineID;
        this.productsNum = productsNum;
        eQueue = queue;
        thread.setName(machineID);
        thread.start();
    }

    public Machine(Queue<Product> queue, int productsNum) {
        this.productsNum = productsNum;
        eQueue = queue;
        thread.start();
    }

    public void setQueue(Queue queue) {
        queueNo = ConcurrencyService.queues.indexOf(queue);
        eQueue = queue;
    }

    @Override
    public void run() {
        Product product;
        for (String q : connection.get(machineID)) {
            Boolean created = false;
            for (Thread t : Thread.getAllStackTraces().keySet()) {
                if (t.getName().equals(q)) {
                    created = true;
                }
            }
            if (!created){
                 new producer(productsNum, nextQueue, q, connection);
            }
               
        }
        for (int i = 0; i < productsNum; i++) {
            synchronized (eQueue) {
                while (eQueue.isEmpty()) {
                    try {
                        eQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                product = eQueue.peek();
                nextQueue.add(product);
                eQueue.remove();
            }
            product.setTempColor(product.getColor());
            try {
                processingTime = rand.nextLong(3000);
                Thread.sleep(processingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (nextQueue) {
                nextQueue.notify();
            }
            System.out.println(machineID + " color: " + product.getColor());
            System.out.println("queue 1 size: " + eQueue.size());
        }

    }
}
