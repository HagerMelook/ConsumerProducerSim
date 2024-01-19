import Queue from "@/Shapes/Queue";
import Machine from "@/Shapes/Machine";

export default class ShapeFactory {
  static createQueue(number, draggable) {
    const queue = new Queue(number, draggable);
    queue.id = `Q${number}`;
    return queue;
  }

  static createMachine(number, draggable) {
    const machine = new Machine(number, draggable);
    machine.id = `M${number}`;
    return machine;
  }
}
