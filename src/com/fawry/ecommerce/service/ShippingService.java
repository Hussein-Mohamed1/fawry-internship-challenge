package com.fawry.ecommerce.service;
import com.fawry.ecommerce.product.Shippable;
import java.util.List;

public class ShippingService {
    private static final double SHIPPING_RATE_PER_KG = 30.0; // 30 per kg

    public static double calculateShippingFee(List<Shippable> shippableItems) {
        if (shippableItems.isEmpty()) {
            return 0.0;
        }
        double totalWeight = shippableItems.stream()
                .mapToDouble(Shippable::getWeight)
                .sum();

        return Math.ceil(totalWeight) * SHIPPING_RATE_PER_KG;
    }

    public static void processShipment(List<Shippable> shippableItems) {
        if (shippableItems.isEmpty()) {
            return;
        }
        System.out.println("** Shipment notice **");
        double totalWeight = 0.0;
        for (Shippable item : shippableItems) {
            double weightInGrams = item.getWeight() * 1000; // Convert kg to grams
            System.out.println("1x " + item.getName() + " " + (int)weightInGrams + "g");
            totalWeight += item.getWeight();
        }

        System.out.println("Total package weight " + totalWeight + "kg");
    }
}