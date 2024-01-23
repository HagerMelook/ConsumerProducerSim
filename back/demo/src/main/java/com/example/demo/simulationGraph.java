package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class simulationGraph {
    int productsNum;
    String[] machines;
    String[] queues;
    ArrayList<String[]> connections;
    colorProduct products;
    HashMap<String,Object> result = new HashMap<>();
    ArrayList<Object> front = new ArrayList<>();

    public simulationGraph(int productsNum, String[] machines, String[] queues, ArrayList<String[]> connections) {
        this.productsNum = productsNum;
        this.machines = machines;
        this.queues = queues;
        this.connections = connections;
        this.products = new colorProduct(productsNum);
    }

    private void setResult(){
        for(String q : queues){
            result.put(q, 0);
        }
        for(String m : machines){
            result.put(m, null);
        }
    }

    public ArrayList<Object> buildSim() {
        setResult();
        HashMap<String ,Queue<Product>> queueArray = new HashMap<>();
        Queue<Product> start = new LinkedList<>();
        Queue<Product> end = new LinkedList<>();
        String startID = null;
        String endID = null;

        for(String q: queues){
            queueArray.put(q, new LinkedList<>());
        }
        new producer(productsNum, queueArray.get("Q1"), products.getProducts(), "Q1", result);
        for(String machine : machines){
            for(String[] c : connections){
                for(String key : queueArray.keySet()){
                    if(key.equals(c[1])){
                        start = queueArray.get(key);
                        startID = c[1];
                    }
                    if(key.equals(c[2])){
                        end = queueArray.get(key);
                        endID = c[2];
                    }
                }
                new Machine(start, startID, end, endID, c[0], productsNum, result, front);
            }
        }
        ArrayList <Object> r = new ArrayList<>();
        for(String key : result.keySet()){
            r.add(result.get(key));
        }
        int size = queues.length - 1;
        String last = queues[size];
        while((int)result.get(last) != productsNum){}
        return front;
    }

}
