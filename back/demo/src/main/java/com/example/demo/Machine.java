package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Machine implements Runnable {
    
    Thread thread = new Thread(this);
    private Long processingTime;
    Random rand = new Random();
    Queue<Product> startQ = new LinkedList<>();
    Queue<Product> nextQ = new LinkedList<>();
    String machineID;
    int productsNum;
    String machineTmpColor = null;
    HashMap<String,Object> result;
    String startID;
    String endID;
    ArrayList<Object> front;
   

    public Machine(Queue<Product> startQ, String startID, Queue<Product> nextQ, String endID,  String machineID, int productsNum, HashMap<String,Object> result, ArrayList<Object> front) {
        this.startQ = startQ;
        this.nextQ = nextQ;
        this.front = front;
        this.machineID = machineID;
        this.productsNum = productsNum;
        this.result =result;
        this.endID = endID;
        this.startID = startID;
        thread.setName(machineID);
        thread.start();
    }


    private void updateResult(String key, Object value){
        for(String k: result.keySet()){
            if(k.equals(key)){
                result.put(key, value);
            }
        }
    }

    private ArrayList<Object> convertToArrayList(){
         ArrayList <Object> r = new ArrayList<>();
        for(String key : result.keySet()){
            r.add(result.get(key));
        }
        return r;
    }

    @Override
    public void run() {
        Product product;
        for (int i = 0; i < productsNum; i++) {
            synchronized (startQ) {
                while (startQ.isEmpty()) {
                    try {
                        startQ.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                product = startQ.peek();
                startQ.remove();
                updateResult(startID, startQ.size());
            }
            try {
                processingTime = rand.nextLong(3000);
                machineTmpColor = product.getColor();
                updateResult(machineID, machineTmpColor);
                System.out.println(result);
                front.add(convertToArrayList());
                Thread.sleep(processingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (nextQ) {
                nextQ.add(product);
                machineTmpColor = null;
                nextQ.notify();
            }
            updateResult(machineID, machineTmpColor);
            updateResult(endID, nextQ.size());
            front.add(convertToArrayList());
            System.out.println(result);
        }

    }
}
