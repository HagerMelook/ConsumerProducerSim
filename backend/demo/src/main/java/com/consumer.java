package com;

import java.util.Random;

public class consumer implements Runnable{

    @Override
    public void run() {
        Random rand = new Random();
        for(int i = 0; i < 5; i++){
            int serviceTime = rand.nextInt(3000);
            try {
                Thread.sleep(serviceTime);
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }
    
}
