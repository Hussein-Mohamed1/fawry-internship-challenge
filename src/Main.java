import com.fawry.ecommerce.model.Customer;
import com.fawry.ecommerce.model.CartItem;
import com.fawry.ecommerce.cart.Cart;
import com.fawry.ecommerce.product.*;
import com.fawry.ecommerce.service.CheckoutService;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Create products
        Cheese cheese = new Cheese("Cheese", 100.0, 10, LocalDate.now().plusDays(30), 0.4);
        TV tv = new TV("TV", 1500.0, 5, 25.0);
        ScratchCard scratchCard = new ScratchCard("Mobile Scratch Card", 50.0, 20);
        Mobile mobile = new Mobile("Mobile Phone", 800.0, 15);
        Biscuits biscuits = new Biscuits("Biscuits", 150.0, 8, LocalDate.now().plusDays(15), 0.7);
        // Create customer with sufficient balance
        Customer customer = new Customer("Hussein Mohamed", 5000.0);
        // Create cart
        Cart cart = new Cart();
        System.out.println("=== E-COMMERCE SYSTEM DEMO ===\n");
        // Test Case 1: Successful checkout with shipping
        System.out.println("Test Case 1: Successful checkout with shipping");
        try {
            cart.add(cheese, 2);
            cart.add(biscuits, 1);
            cart.add(scratchCard, 1);
            System.out.println("Cart contents:");
            for (CartItem item : cart.getItems()) {
                System.out.println("- " + item.getQuantity() + "x " + item.getProduct().getName() +
                        " $" + item.getProduct().getPrice() + " each");
            }
            System.out.println();
            CheckoutService.checkout(customer, cart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("\n" + "=".repeat(50) + "\n");

        // Test Case 2: Error - Empty cart
        System.out.println("Test Case 2: Error - Empty cart");
        try {
            Cart emptyCart = new Cart();
            CheckoutService.checkout(customer, emptyCart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("\n" + "=".repeat(50) + "\n");

        // Test Case 3: Error - Insufficient balance
        System.out.println("Test Case 3: Error - Insufficient balance");
        try {
            Customer poorCustomer = new Customer("Poor Customer", 50.0);
            Cart expensiveCart = new Cart();
            expensiveCart.add(tv, 2);
            CheckoutService.checkout(poorCustomer, expensiveCart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("\n" + "=".repeat(50) + "\n");

        // Test Case 4: Error - Out of stock
        System.out.println("Test Case 4: Error - Out of stock");
        try {
            Cart outOfStockCart = new Cart();
            outOfStockCart.add(cheese, 50); // Requesting more than available (10)
            CheckoutService.checkout(customer, outOfStockCart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("\n" + "=".repeat(50) + "\n");

        // Test Case 5: Error - Expired product
        System.out.println("Test Case 5: Error - Expired product");
        try {
            Cheese expiredCheese = new Cheese("Expired Cheese", 100.0, 5, LocalDate.now().minusDays(1), 0.5);
            Cart expiredCart = new Cart();
            expiredCart.add(expiredCheese, 1);
            CheckoutService.checkout(customer, expiredCart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("\n" + "=".repeat(50) + "\n");

        // Test Case 6: Mixed cart (shippable and non-shippable)
        System.out.println("Test Case 6: Mixed cart (shippable and non-shippable items)");
        try {
            Cart mixedCart = new Cart();
            mixedCart.add(tv, 1);
            mixedCart.add(mobile, 2);
            mixedCart.add(scratchCard, 1);
            System.out.println("Cart contents:");
            for (CartItem item : mixedCart.getItems()) {
                System.out.println("- " + item.getQuantity() + "x " + item.getProduct().getName() +
                        " $" + item.getProduct().getPrice() + " each");
            }
            System.out.println();
            CheckoutService.checkout(customer, mixedCart);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("\n" + "=".repeat(50) + "\n");

        // Test Case 7: Non-shippable items only
        System.out.println("Test Case 7: Non-shippable items only (no shipping fee)");
        try {
            Cart digitalCart = new Cart();
            digitalCart.add(scratchCard, 3);
            System.out.println("Cart contents:");
            for (CartItem item : digitalCart.getItems()) {
                System.out.println("- " + item.getQuantity() + "x " + item.getProduct().getName() +
                        " $" + item.getProduct().getPrice() + " each");
            }
            System.out.println();
            CheckoutService.checkout(customer, digitalCart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("\n=== DEMO COMPLETED ===");
    }
}