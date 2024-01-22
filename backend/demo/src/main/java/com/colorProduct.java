package com;

import java.util.Queue;

public class colorProduct {

    private Queue<Product> products;
    ConcurrencyService color = new ConcurrencyService();

    public Queue<Product> getProducts() {
        return products;
    }

    public colorProduct(Long productsNum) {
        for(Long i = 0l; i < productsNum; i++){
            Product product =  color.setProductsColor();
            products.add(product);
        }
    }
    
}
