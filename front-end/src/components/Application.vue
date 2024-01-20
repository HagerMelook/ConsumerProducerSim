<template>
  <div id="app" class="container mt-4">
    <h1 class="mb-4 text-center">Production Line Simulation</h1>

    <div class="d-flex justify-content-between mb-4">
      <div class="btn-group">
        <button class="btn btn-primary me-2" @click="addQueue"><i class="fas fa-plus"></i> Add Queue</button>
        <button class="btn btn-success" @click="addMachine"><i class="fas fa-plus"></i> Add Machine</button>
      </div>
      <div class="ms-2">
        <div class="input-group mb-3">
          <select class="form-select" v-model="selectedQueue">
            <option value="">Select Queue</option>
            <option v-for="queue in queues" :key="queue.id" :value="queue.id">
              {{ queue.name }}
            </option>
          </select>
          <select class="form-select" v-model="selectedMachine">
            <option value="">Select Machine</option>
            <option v-for="machine in machines" :key="machine.id" :value="machine.id">
              {{ machine.name }}
            </option>
          </select>
          <button class="btn btn-primary" @click="connectObjects">
            Connect
          </button>
        </div>
      </div>
      <div class="ms-2 d-flex align-items-center">
        <label for="productCountInput" class="me-2">Products no:</label>
        <input
          id="productCountInput"
          type="number"
          class="form-control"
          v-model="productCountInput"
          :disabled="simulationRunning"
          style="width: 80px;"
          min="0"
        />
      </div>
      <div class="ms-2">
        <button class="btn btn-warning me-2" @click="startSimulation" :disabled="simulationRunning">
          <i class="fas fa-play"></i> Start
        </button>
        <button class="btn btn-danger me-2" @click="stopSimulation" :disabled="!simulationRunning">
          <i class="fas fa-stop"></i> Stop
        </button>
        <button class="btn btn-info me-2" @click="resetSimulation">
          <i class="fas fa-redo"></i> Reset
        </button>
        <button class="btn btn-primary" @click="replySimulation">
          <i class="fas fa-reply"></i> Reply
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
      selectedQueue: "",
      selectedMachine: "",
      connections: [], 
      productCountInput: 0,
    };
  },
  methods: {
    addQueue() {
      this.queueCount++;
      const queue = ShapeFactory.createQueue(this.queueCount, true);
      queue.group.on("dragmove", (event) => this.handleDrag(event, queue.group));
      this.layer.add(queue.group);
      this.queues.push(queue);
      console.log(queue);
      this.stage.draw();
    },
    addMachine() {
      this.machineCount++;
      const machine = ShapeFactory.createMachine(this.machineCount, true);
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
 
      }
    },
    // update the arrows when objects are dragged
    updateArrows() {
      this.connections.forEach((connection) => {
        const queue = this.queues.find((q) => q.id === connection.queueId);
        const machine = this.machines.find((m) => m.id === connection.machineId);

        if (queue && machine) {
          const queueCenter = {
          x: queue.group.getClientRect().x + queue.group.getClientRect().width / 2,
          y: queue.group.getClientRect().y + queue.group.getClientRect().height / 2,
          };

          const machineCenter = {
            x: machine.group.getClientRect().x + machine.group.getClientRect().width / 2,
            y: machine.group.getClientRect().y + machine.group.getClientRect().height / 2,
          };
          const arrow = this.layer.findOne((node) => node.name() === `arrow-${queue.id}-${machine.id}`);

          if (arrow) {
            arrow.points([queueCenter.x, queueCenter.y, machineCenter.x, machineCenter.y]);
          }
        }
      });

      this.stage.draw();
    },

    // Modifying handleDrag method to call updateArrows
    handleDrag(event, item) {
      this.updateArrows();
    },

    connectObjects() {
      
      if (this.selectedQueue && this.selectedMachine) {
        const queue = this.queues.find((q) => q.id === this.selectedQueue);
        const machine = this.machines.find((m) => m.id === this.selectedMachine);

        if (queue && machine) {


          //  a connection already exists
          const existingConnection = this.connections.find(
            (connection) => connection.queueId === queue.id && connection.machineId === machine.id
          );

          if (existingConnection) {
            alert("Connection already exists between this machine and queue.");
            return;
          }

          const queueCenter = {
            x: queue.group.getClientRect().x + queue.group.getClientRect().width / 2,
            y: queue.group.getClientRect().y + queue.group.getClientRect().height / 2,
          };

          const machineCenter = {
            x: machine.group.getClientRect().x + machine.group.getClientRect().width / 2,
            y: machine.group.getClientRect().y + machine.group.getClientRect().height / 2,
          };

          // Generate a unique name for the arrow based on connected objects
          const arrowName = `arrow-${queue.id}-${machine.id}`;

          const arrow = new Konva.Arrow({
            name: arrowName,
            points: [queueCenter.x, queueCenter.y, machineCenter.x, machineCenter.y],
            pointerLength: 10,
            pointerWidth: 10,
            fill: "black",
            stroke: "black",
            strokeWidth: 2,
          });

          this.layer.add(arrow);

          // Move the queues and machines to the top
          queue.group.moveToTop();
          machine.group.moveToTop();

          this.stage.draw();

          // Save the connection
          const connection = {
            queueId: queue.id,
            machineId: machine.id,
          };
          this.connections.push(connection);
        }
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

