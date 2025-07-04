package com.fawry.ecommerce.cart;
import com.fawry.ecommerce.model.CartItem;
import com.fawry.ecommerce.model.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void add(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (!product.isAvailable(quantity)) {
            throw new IllegalArgumentException("Not enough stock available for " + product.getName());
        }
        // Check if product already exists in cart
        for (CartItem item : items) {
            if (item.getProduct().equals(product)) {
                int newQuantity = item.getQuantity() + quantity;
                if (!product.isAvailable(newQuantity)) {
                    throw new IllegalArgumentException("Not enough stock available for " + product.getName());
                }
                item.setQuantity(newQuantity);
                return;
            }
        }
        // Add new item to cart
        items.add(new CartItem(product, quantity));
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getSubtotal() {
        return items.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }

    public void clear() {
        items.clear();
    }
}