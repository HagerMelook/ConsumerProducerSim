import Konva from "konva";

export default class Queue {
  constructor(number, draggable) {
    this.group = new Konva.Group({
      draggable,
    });

    this.rect = new Konva.Rect({
      x: 0,
      y: 0,
      width: 70,
      height: 60,
      fill: "#e74c3c",
      name: `Q${number}`,
    });

    this.text = new Konva.Text({
      text: `Q${number}`,
      x: -3,
      y: 10,
      fontSize: 14,
      fill: "#fff",
      width: this.rect.width(),
      align: "center",
    });

    this.countText = new Konva.Text({
      text: "Count: 0",
      x: -3,
      y:30,
      fontSize: 12,
      fill: "#fff",
      width: this.rect.width(),
      align: "center",
    });

    this.group.add(this.rect, this.text, this.countText);
  }
}
