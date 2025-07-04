package com.fawry.ecommerce.product;
import com.fawry.ecommerce.model.ExpirableProduct;
import java.time.LocalDate;

public class Biscuits extends ExpirableProduct implements Shippable {
    private double weight;

    public Biscuits(String name, double price, int quantity, LocalDate expirationDate, double weight) {
        super(name, price, quantity, expirationDate);
        this.weight = weight;
    }

    @Override
    public boolean requiresShipping() {
        return true;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}