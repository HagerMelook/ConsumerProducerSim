package com.example.demo;

import java.util.LinkedList;
import java.util.Queue;

public class colorProduct {

    private Queue<Product> products = new LinkedList<>();
    ConcurrencyService color = new ConcurrencyService();

    public Queue<Product> getProducts() {
        return products;
    }

    public colorProduct(int productsNum) {
        for(int i = 0; i < productsNum; i++){
            Product product =  color.setProductsColor();
            products.add(product);
        }
    }
    
}
