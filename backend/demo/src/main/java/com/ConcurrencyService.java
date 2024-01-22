package com;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
public class ConcurrencyService {
    private int usedColor;
    String [] possibleColors;
    public static ArrayList<Queue> queues;
    ArrayList<Machine> Machines;
    ArrayList<Integer> queuesSize;
    int tempColor;
    public ConcurrencyService() {
        usedColor=-1;
        tempColor=10;
        possibleColors = new String[50];
        queues = new ArrayList<Queue>();
        colorInitialization();
    }

    public void addProduct(){
        usedColor++;
        Product product = new Product(RandomColor(usedColor));
        queues.get(0).add(product);
    }

    public Product setProductsColor(){
        usedColor++;
        Product product = new Product(RandomColor(usedColor));
        return product;
        
    }

    public void addQueue(){
        Queue<Product> queue = new LinkedList<Product>();
        queues.add(queue);
    }
    // public void addMachine(){
    //     Machines.add(new Machine(RandomColor(tempColor)));
    //     tempColor++;
    // }
    
    public ArrayList<Integer> queuesSize(){
        for(Queue queue: queues){
            queuesSize.add(queue.size());
        }
        return queuesSize;
    }
    private String RandomColor(int usedColor){
        return possibleColors[usedColor];
    }

    private void colorInitialization(){
        possibleColors[0]="Red";
        possibleColors[1]= "Blue";
        possibleColors[2]="Green";
        possibleColors[3]="Brown";
        possibleColors[4]="Pink";
        possibleColors[5]= "Black";
        possibleColors[6]="Lavender";
        possibleColors[7]="Yellow";
        possibleColors[8]="Wight";
        possibleColors[9]="Orange";
        possibleColors[10]="Rose";
        possibleColors[11]= "Sky";
        possibleColors[12]="Lime";
        possibleColors[13]="Coffee";
        possibleColors[14]="Watermelon";
        possibleColors[15]= "Steel";
        possibleColors[16]="Purple";
        possibleColors[17]="Latte";
        possibleColors[18]="Mauve";
        possibleColors[19]="Gold";
        possibleColors[20]="Blush";
        possibleColors[21]= "Navy";
        possibleColors[22]="Mint";
        possibleColors[23]="Caramel";
        possibleColors[24]="Peach";
        possibleColors[25]= "Salmon";
        possibleColors[26]="Magenta";
        possibleColors[27]="Beige";
        possibleColors[28]="Gray";
        possibleColors[29]="Honey";
        possibleColors[30]="Duke blue";
        possibleColors[31]= "Dutch White";
        possibleColors[32]="Deep Taupe";
        possibleColors[33]="Deep cerise";
        possibleColors[34]="Dark slate gray";
        possibleColors[35]= "corn";
        possibleColors[36]="Copper red";
        possibleColors[37]="Citron";
        possibleColors[38]="Chocolate";
        possibleColors[39]="Canary";
        possibleColors[40]="British racing green";
        possibleColors[41]= "Blue Sapphire";
        possibleColors[42]="Blanched";
        possibleColors[43]="Aqua";
        possibleColors[44]="Finn";
        possibleColors[45]= "SMASHED PUMPKIN";
        possibleColors[46]="Sepia";
        possibleColors[47]="Silver";
        possibleColors[48]="Frost";
        possibleColors[49]="Cotton";
    }
}
