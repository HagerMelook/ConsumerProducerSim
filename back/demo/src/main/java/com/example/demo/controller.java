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


@CrossOrigin
@RequestMapping("/")
@RestController
public class controller {

    @PostMapping("/start/{num}")
    public ArrayList<Object> start(@RequestBody simulationGraph body, @PathVariable("num") int productsNum) {
        simulationGraph startSim = new simulationGraph(productsNum, body.getMachines(), body.getQueues(), body.getConnections());
        return startSim.buildSim();
    }

}
