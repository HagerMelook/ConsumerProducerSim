// package com.example.demo;

// import java.util.HashMap;
// import java.util.Queue;

// public class simulationMap {
//     int productsNum;
//     String[] machines;
//     String[] queues;
//     Queue<Product> nextQueue;
//     HashMap<String, String[]> connection;

//     public simulationMap(String[] machines, String[] queues, HashMap<String, String[]> connection, int productsNum) {
//         this.machines = machines;
//         this.queues = queues;
//         this.productsNum = productsNum;
//         this.connection = connection;
//     }

//     public void setQueue(Queue<Product> nextQueue, String queueID){
//         this.nextQueue = nextQueue;
//     }


//     public void buildThreads() {
//         for (String queue : queues) {
//             producer setQueue = new producer(nextQueue.size(), nextQueue, queue);
//             for(int i = 0; i < connection.get(queue).length; i++){
//                 Machine machine = new Machine(setQueue.getQueue(), nextQueue.size());
//             }
//         }

//     }

// }
