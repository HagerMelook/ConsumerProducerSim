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

    <div class="simulation-area" @mousedown="handleArenaClick" style="background-color: #fff;">
      <div v-for="(queue, index) in queues" :key="index" class="block m-2"
        :style="{ left: queue.left + 'px', top: queue.top + 'px', position: 'absolute' }"
        @mousedown.stop.prevent="startDrag(queue, 'Q', index)">
        <div class="queue">
          Q{{ queue.number }}
          {{ queue.count }}
        </div>
      </div>

      <div v-for="(machine, index) in machines" :key="index" class="block m-2"
        :style="{ left: machine.left + 'px', top: machine.top + 'px', position: 'absolute' }"
        @mousedown.stop.prevent="startDrag(machine, 'M', index)">
        <div class="machine">
          M{{ machine.number }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      queues: [],
      machines: [],
      queueCount: 0,
      machineCount: 0,
      simulationRunning: false,
      dragging: false,
      draggedItem: null,
      initialMouseX: 0,
      initialMouseY: 0,
    }
  },
  methods: {
    addQueue() {
      this.queueCount++;
      this.queues.push({ number: this.queueCount, count: 0, left: 0, top: 0 });
    },
    addMachine() {
      this.machineCount++;
      this.machines.push({ number: this.machineCount, left: 0, top: 0 });
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
    startDrag(item, type, index) {
      if (!this.simulationRunning) {
        this.dragging = true;
        this.draggedItem = item;
        this.initialMouseX = event.clientX;
        this.initialMouseY = event.clientY;

        // Add mouseup event listener
        document.addEventListener('mouseup', () => this.stopDrag(type, index));
      }
    },
    stopDrag(type, index) {
      this.dragging = false;
      this.draggedItem = null;

      // Remove mouseup event listener 
      document.removeEventListener('mouseup', this.stopDrag);

    },
    handleDrag(event) {
      if (this.dragging && this.draggedItem) {
        const deltaX = event.clientX - this.initialMouseX;
        const deltaY = event.clientY - this.initialMouseY;

        // Update the position of the dragged item
        this.draggedItem.left += deltaX;
        this.draggedItem.top += deltaY;

        // Update initial mouse positions
        this.initialMouseX = event.clientX;
        this.initialMouseY = event.clientY;
      }
    },
    handleArenaClick() {
      this.dragging = false;
      this.draggedItem = null;
    },
  },
  mounted() {
    document.addEventListener('mousemove', this.handleDrag);
  },
  destroyed() {
    document.removeEventListener('mousemove', this.handleDrag);
  },
  computed: {
    dragOptions() {
      return {
        animation: 0,
        group: 'machines-queues',
        draggable: '.machine, .queue',
      };
    },
  },
  watch: {
    dragging(value) {
      if (!value) {
        this.initialMouseX = 0;
        this.initialMouseY = 0;
      }
    },
  },
}

</script>
<style scoped>
.machine,
.queue {
  cursor: move;
}

.simulation-area {
  border: 2px solid #ddd;
  padding: 20px;
  height: 500px;
  position: relative;
}

.machine {
  width: 50px;
  height: 50px;
  background-color: #3498db;
  color: #fff;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.queue {
  width: 70px;
  height: 30px;
  background-color: #e74c3c;
  color: #fff;
  border-radius: 5px;
  display: flex;
  align-items: center;
  justify-content: center;
}

button {
  margin-left: 7px;
}
</style>
