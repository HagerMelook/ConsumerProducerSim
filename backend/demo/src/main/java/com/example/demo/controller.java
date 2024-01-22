package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@CrossOrigin
@RequestMapping("/")
@RestController
public class controller {

    @PostMapping("/start/{num}")
    public void start(@RequestParam("machines") String[] machines, @RequestParam("queues") String[] queues,
    /*@RequestParam("connection") HashMap<String, String[]> connection*/ @PathVariable("num") int productsNum) {
        HashMap<String, String[]> connection =new HashMap<>();
        String []tmp = {"M1","M2"};
        String []tmp2 = {"Q2"};
        String []tmp3 = {"Q2"};
        connection.put("M1", tmp2);
        connection.put("Q2", null);
        connection.put("Q1", tmp);
        connection.put("M2", tmp3);
        colorProduct products = new colorProduct(productsNum);
        producer setQueue = new producer(productsNum, products.getProducts(), "Q1", connection);
        for(String machine : connection.get("Q1")){
            new Machine(setQueue.getQueue(), productsNum, connection ,machine);
        }
    }
}
