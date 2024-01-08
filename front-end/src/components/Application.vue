<template>
  <div id="app" class="container mt-4">
    <h1 class="mb-4 text-center">Production Line Simulation</h1>

    <div class="d-flex justify-content-between mb-4">
      <div class="btn-group">
        <button class="btn btn-primary me-2" @click="addQueue"><i class="fas fa-plus"></i> Add Queue</button>
        <button class="btn btn-success" @click="addMachine"><i class="fas fa-plus"></i> Add Machine</button>
      </div>
      <div>
        <button class="btn btn-warning me-2" @click="startSimulation" :disabled="simulationRunning">
          <i class="fas fa-play"></i> Start Simulation
        </button>
        <button class="btn btn-danger" @click="stopSimulation" :disabled="!simulationRunning">
          <i class="fas fa-stop"></i> Stop Simulation
        </button>
        <button class="btn btn-info" @click="resetSimulation">
          <i class="fas fa-redo"></i> Reset Simulation
        </button>
        <button class="btn btn-primary" @click="replySimulation">
          <i class="fas fa-reply"></i> Reply Simulation
        </button>
      </div>
    </div>

    <div id="simulation-container" class="border p-4 position-relative" style="background-color: #fff;"></div>
  </div>
</template>

<script>
import   ShapeFactory  from '../Shapes/ShapeFactory.js';


export default {
  data() {
    return {
      
      queues: [],
      machines: [],
      queueCount: 0,
      machineCount: 0,
      simulationRunning: false,
      stage: null,
      layer: null,
      dragBoundFunc: (pos) => ({ x: pos.x, y: pos.y }),
    };
  },
  methods: {
    addQueue() {
      this.queueCount++;
      const queue = ShapeFactory.createQueue(this.queueCount, !this.simulationRunning);
      queue.group.on("dragmove", (event) => this.handleDrag(event, queue.group));
      this.layer.add(queue.group);
      this.queues.push(queue);
      this.stage.draw();
    },
    addMachine() {
      this.machineCount++;
      const machine = ShapeFactory.createMachine(this.machineCount, !this.simulationRunning);
      machine.group.on("dragmove", (event) => this.handleDrag(event, machine.group));
      this.layer.add(machine.group);
      this.machines.push(machine);
      this.stage.draw();
    },
    startSimulation() {
      this.simulationRunning = true;
      /////////////////////////////
    },
    stopSimulation() {
      this.simulationRunning = false;
      ///////////////////////////
    },
    resetSimulation() {
      this.simulationRunning = false;
      ///////////////////////////
    },
    replySimulation() {
      this.resetSimulation();
      this.startSimulation();
    },
    handleDrag(event, item) {
      if (this.simulationRunning) {
        event.cancelBubble = true;
        item.draggable(false);
      }
    },
  },
  mounted() {
    this.stage = new Konva.Stage({
      container: "simulation-container",
      width: window.innerWidth,
      height: window.innerHeight - 200,
    });

    this.layer = new Konva.Layer();
    this.stage.add(this.layer);
  },
};
</script>

<style>
#simulation-container {
  border: 2px solid #ddd;
  padding: 20px;
  height: 500px;
  position: relative;
}
</style>
