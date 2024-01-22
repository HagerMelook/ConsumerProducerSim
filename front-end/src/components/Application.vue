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
          <select class="form-select" v-model="selectedFromObject">
            <option value="">From</option>
            <option v-for="object in allObjects" :key="object.id" :value="object.id">
              {{ object.name }}
            </option>
          </select>

          <select class="form-select" v-model="selectedToObject">
            <option value="">To</option>
            <option v-for="object in allObjects" :key="object.id" :value="object.id">
              {{ object.name }}
            </option>
          </select>

          <button class="btn btn-primary" @click="connectObjects">
            Connect
          </button>
        </div>
      </div>
      <div class="ms-2 d-flex align-items-center">
        <label for="productCountInput" class="me-2">Products no:</label>
        <input id="productCountInput" type="number" class="form-control" v-model="productCountInput"
          :disabled="simulationRunning" style="width: 80px;" min="0" />
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
import ShapeFactory from '../Shapes/ShapeFactory.js';


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
      productSimulationInterval: null,
      productCountInput: 0,
      productCount: 0,
      selectedFromObject: "",
      selectedToObject: "",
      allObjects: [], // Combined array of machines and queues
    };
  },
  methods: {
    addQueue() {
      this.queueCount++;
      const queue = ShapeFactory.createQueue(this.queueCount, true);
      queue.group.on("dragmove", (event) => this.handleDrag(event, queue.group));
      this.layer.add(queue.group);
      this.queues.push(queue);
      this.allObjects.push(queue);
      this.stage.draw();
    },
    addMachine() {
      this.machineCount++;
      const machine = ShapeFactory.createMachine(this.machineCount, true);
      machine.group.on("dragmove", (event) => this.handleDrag(event, machine.group));
      this.layer.add(machine.group);
      this.machines.push(machine);
      this.allObjects.push(machine);
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
      if (!this.simulationRunning) {
        console.log("s")
        event.cancelBubble = true;
        this.updateArrows();
      }
    },
    // update the arrows when objects are dragged
    updateArrows() {
      this.connections.forEach((connection) => {
        const queue = this.queues.find((q) => q.id === connection.queueId);
        const machine = this.machines.find((m) => m.id === connection.machineId);

        if (queue && machine) {
          const queueCenter = {
            x: queue.group.getClientRect().x + queue.group.getClientRect().width,
            y: queue.group.getClientRect().y + queue.group.getClientRect().height / 2,
          };

          const machineCenter = {
            x: machine.group.getClientRect().x + machine.group.getClientRect().width,
            y: machine.group.getClientRect().y + machine.group.getClientRect().height / 2,
          };
          var arrow = this.layer.findOne((node) => node.name() === `arrow-${queue.id}-${machine.id}`);

          if (arrow) {
            arrow.points([queueCenter.x, queueCenter.y, machineCenter.x - machine.group.getClientRect().width, machineCenter.y]);
          } else {
            arrow = this.layer.findOne((node) => node.name() === `arrow-${machine.id}-${queue.id}`);
            arrow.points([machineCenter.x, machineCenter.y, queueCenter.x - queue.group.getClientRect().width, queueCenter.y]);
          }
        }
      });

      this.stage.draw();
    },


    connectObjects() {
      if (this.selectedFromObject && this.selectedToObject) {

        const fromObject = this.allObjects.find((item) => item.id === this.selectedFromObject);
        const toObject = this.allObjects.find((item) => item.id === this.selectedToObject);

        if (fromObject && toObject) {
          if (fromObject.type === 'queue' && toObject.type === 'machine') {
            const queue = fromObject;
            const machine = toObject;
            if (!this.isConnectionExists(queue.id, machine.id)) {
              const connection = {
                queueId: queue.id,
                machineId: machine.id,
              };
              this.connections.push(connection);
              this.drawArrow(queue.group, machine.group, queue.id, machine.id);
            } else {
              alert("Connection already exists between this machine and queue.");
            }
          } else if (fromObject.type === 'machine' && toObject.type === 'queue') {
            const machine = fromObject;
            const queue = toObject;
            if (!this.isConnectionExists(queue.id, machine.id)) {
              const connection = {
                queueId: queue.id,
                machineId: machine.id,
              };
              this.connections.push(connection);
              this.drawArrow(machine.group, queue.group, machine.id, queue.id);

              console.log(`Connected Machine ${machine.name} to Queue ${queue.name}`);
            } else {
              alert("Connection already exists between this machine and queue.");
            }
          }
        }
      } else {
        alert("Please select 'From' and 'To' objects.");
      }
    },
    isConnectionExists(queueId, machineId) {
      return this.connections.some((connection) => connection.queueId === queueId && connection.machineId === machineId);
    },
    drawArrow(fromGroup, toGroup, fromId, toId) {
      const fromCenter = {
        x: fromGroup.getClientRect().x + fromGroup.getClientRect().width,
        y: fromGroup.getClientRect().y + fromGroup.getClientRect().height / 2,
      };

      const toCenter = {
        x: toGroup.getClientRect().x,
        y: toGroup.getClientRect().y + toGroup.getClientRect().height / 2,
      };


      const arrowName = `arrow-${fromId}-${toId}`;
      console.log(arrowName)
      const arrow = new Konva.Arrow({
        name: arrowName,
        points: [fromCenter.x, fromCenter.y, toCenter.x, toCenter.y],
        pointerLength: 10,
        pointerWidth: 10,
        fill: "black",
        stroke: "black",
        strokeWidth: 2,
      });
      this.layer.add(arrow);
      this.stage.draw();
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

