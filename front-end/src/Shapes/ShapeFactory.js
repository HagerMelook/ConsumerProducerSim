
import Queue from "@/Shapes/Queue";
import Machine from "@/Shapes/Machine";

export default class ShapeFactory {
  static createQueue(number, draggable) {
    return new Queue(number, draggable);
  }

  static createMachine(number, draggable) {
    return new Machine(number, draggable);
  }
}
