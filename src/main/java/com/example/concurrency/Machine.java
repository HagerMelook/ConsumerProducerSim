package com.example.concurrency;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Machine implements Runnable{
    Thread thread = new Thread();
    private Long processingTime;
    Random rand = new Random();
    Queue<Product> eQueue = new LinkedList<>();
    String ProductColor;
    int queueNo;
    public Machine(String Color) {
        processingTime = rand.nextLong();
        ProductColor = Color;
        thread.start();
    }
    
    public void setQueue(Queue queue){
        queueNo = ConcurrencyService.queues.indexOf(queue);
        eQueue = queue;
    }
    @Override
    public void run() {
        synchronized(thread){
            while(eQueue.isEmpty()){
                try {
                    thread.wait();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            Product product = eQueue.peek();
            product.setTempColor(ProductColor);
            try {
                Thread.sleep(processingTime);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            ConcurrencyService.queues.get(queueNo+1).add(product);
            eQueue.notify();
            run();
        // TODO Auto-generated method stub
        
    }
}
}
