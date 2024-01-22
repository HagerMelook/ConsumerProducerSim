package com;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
public class controller {

    @GetMapping("start/{num}")
    public void start(/*@RequestBody ArrayList<Object> machines, @RequestBody ArrayList<Object> queues,
            @RequestBody ArrayList<Object> connection,*/ @PathVariable("num") Long productsNum) {
                colorProduct products = new colorProduct(productsNum);
                producer setQueue = new producer(productsNum, products.getProducts(), "Q1");
                Machine machine = new Machine(setQueue.getQueue());
    }

}
