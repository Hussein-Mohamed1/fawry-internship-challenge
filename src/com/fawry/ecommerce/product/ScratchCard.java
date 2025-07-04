package com.fawry.ecommerce.product;
import com.fawry.ecommerce.model.Product;

public class ScratchCard extends Product {

    public ScratchCard(String name, double price, int quantity) {
        super(name, price, quantity);
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public boolean requiresShipping() {
        return false; // Scratch cards don't require shipping
    }
}