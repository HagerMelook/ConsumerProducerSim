import Konva from "konva";

export default class Queue {
  constructor(number, draggable) {
    this.id = "";
    this.name = `Q${number}`;
    this.products = []; // Initialize the products array which will contain the random colored products 

    this.group = new Konva.Group({
      draggable,
      name: this.name,
    });

    this.rect = new Konva.Rect({
      x: 0,
      y: 0,
      width: 70,
      height: 60,
      fill: "#ffffff",
      stroke: "#000000",
      strokeWidth: 2,
      cornerRadius: 2,
    });

    this.text = new Konva.Text({
      text: this.name,
      x: -3,
      y: 10,
      fontSize: 14,
      fill: "#000000",
      width: this.rect.width(),
      align: "center",
    });

    this.countText = new Konva.Text({
      text: `Count: ${this.products.length}`, // Update the countText to be the length of the products array as the count will decrease during simulaton
      x: -3,
      y: 30,
      fontSize: 12,
      fill: "#000000",
      width: this.rect.width(),
      align: "center",
    });

    this.group.add(this.rect, this.text, this.countText);
  }
}
