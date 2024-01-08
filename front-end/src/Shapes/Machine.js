import Konva from "konva";

export default class Machine {
  constructor(number, draggable) {
    this.group = new Konva.Group({
      draggable,
    });

    this.circle = new Konva.Circle({
      x: 50,
      y: 50,
      radius: 30,
      fill: "#3498db",
      name: `M${number}`,
    });

    this.text = new Konva.Text({
      text: `M${number}`,
      x: -this.circle.radius()+50,
      y: -this.circle.radius()+50,
      fontSize: 14,
      fill: "#fff",
      width: this.circle.radius() * 2,
      height: this.circle.radius() * 2,
      align: "center",
      verticalAlign: "middle",
    });

    this.group.add(this.circle, this.text);
  }
}
