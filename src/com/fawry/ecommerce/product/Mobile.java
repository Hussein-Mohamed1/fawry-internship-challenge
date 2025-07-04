package com.fawry.ecommerce.product;
import com.fawry.ecommerce.model.Product;

public class Mobile extends Product {

    public Mobile(String name, double price, int quantity) {
        super(name, price, quantity);
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public boolean requiresShipping() {
        return false;
    }
}