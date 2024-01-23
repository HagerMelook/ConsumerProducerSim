package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;


@CrossOrigin
@RequestMapping("/")
@RestController
public class controller {

    @PostMapping("/start/{num}")
    public ArrayList<Object> start(@RequestParam("machines") String[] machines, @RequestParam("queues") String[] queues,
    @RequestParam("connection") ArrayList<String[]> connections, @PathVariable("num") int productsNum) {
        ArrayList<String[]> c = new ArrayList<>();
        String[] m = {"M1","M2"};
        String[]q = {"Q1","Q2"};
        String[] a = {"M1","Q1","Q2"};
        String[] b = {"M2","Q1","Q2"};
        c.add(a);
        c.add(b);
        simulationGraph startSim = new simulationGraph(productsNum, m, q, c);
        return startSim.buildSim();
    }

    // @GetMapping("/replay/{num}")
    // public HashMap<String,Object> replay(@RequestParam("machines") String[] machines, @RequestParam("queues") String[] queues,
    // @RequestParam("connection") ArrayList<String[]> connections, @PathVariable("num") int productsNum) {

    //     simulationMap startSim = new simulationMap(productsNum, machines, queues, connections);
    //     return startSim.buildSim();
    // }

}
