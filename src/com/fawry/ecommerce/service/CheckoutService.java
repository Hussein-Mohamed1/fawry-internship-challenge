package com.fawry.ecommerce.service;
import com.fawry.ecommerce.cart.Cart;
import com.fawry.ecommerce.model.CartItem;
import com.fawry.ecommerce.model.Customer;
import com.fawry.ecommerce.model.Product;
import com.fawry.ecommerce.product.Shippable;
import java.util.ArrayList;
import java.util.List;

public class CheckoutService {

    public static void checkout(Customer customer, Cart cart) {
        // Validate cart is not empty
        if (cart.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }
        // Validate stock availability and expiration
        validateCartItems(cart);
        // Calculate totals
        double subtotal = cart.getSubtotal();
        List<Shippable> shippableItems = getShippableItems(cart);
        double shippingFee = ShippingService.calculateShippingFee(shippableItems);
        double totalAmount = subtotal + shippingFee;
        // Validate customer balance
        if (!customer.hasEnoughBalance(totalAmount)) {
            throw new IllegalArgumentException("Customer's balance is insufficient");
        }
        // Process shipment if needed
        if (!shippableItems.isEmpty()) {
            ShippingService.processShipment(shippableItems);
        }
        // Process payment
        customer.deductBalance(totalAmount);
        // Update product quantities
        updateProductQuantities(cart);
        // Print receipt
        printReceipt(cart, subtotal, shippingFee, totalAmount, customer.getBalance());
        // Clear cart
        cart.clear();
    }

    private static void validateCartItems(Cart cart) {
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            // Check if product is expired
            if (product.isExpired()) {
                throw new IllegalArgumentException(product.getName() + " is expired");
            }
            // Check if enough stock is available
            if (!product.isAvailable(item.getQuantity())) {
                throw new IllegalArgumentException(product.getName() + " is out of stock");
            }
        }
    }

    private static List<Shippable> getShippableItems(Cart cart) {
        List<Shippable> shippableItems = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            if (product.requiresShipping() && product instanceof Shippable) {
                // Add each quantity as separate item for shipping calculation
                for (int i = 0; i < item.getQuantity(); i++) {
                    shippableItems.add((Shippable) product);
                }
            }
        }
        return shippableItems;
    }

    private static void updateProductQuantities(Cart cart) {
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() - item.getQuantity());
        }
    }

    private static void printReceipt(Cart cart, double subtotal, double shippingFee, double totalAmount, double remainingBalance) {
        System.out.println("** Checkout receipt **");
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            System.out.println(item.getQuantity() + "x " + product.getName() + " " + (int)item.getTotalPrice());
        }
        System.out.println("----------------------");
        System.out.println("Subtotal " + (int)subtotal);
        System.out.println("Shipping " + (int)shippingFee);
        System.out.println("Amount " + (int)totalAmount);
        System.out.println("balance after payment: " + remainingBalance);
        System.out.println("END.");
    }
}