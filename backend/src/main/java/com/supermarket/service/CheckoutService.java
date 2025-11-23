package com.supermarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service containing the core business logic for calculating the basket total.
 */
@Service
public class CheckoutService {

    private final PricingService pricingService;
    private final Map<String, Integer> basket = new HashMap<>();

    @Autowired
    public CheckoutService(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    /**
     * Adds one unit of an item to the basket.
     *
     * @param item The name of the item scanned.
     */
    public void scanItem(String item) {
        if (pricingService.getPrice(item) == null) {
            throw new IllegalArgumentException("Item not found in pricing catalog: " + item);
        }
        // Increment the count for the scanned item
        basket.merge(item, 1, Integer::sum);
    }

    /**
     * Calculates the total price of all items currently in the basket.
     * This logic prioritizes special offers over unit pricing.
     *
     * @return The final total price as an integer.
     */
    public int calculateTotal() {
        int total = 0;

        // Iterate through all items in the current basket
        for (Map.Entry<String, Integer> entry : basket.entrySet()) {
            String itemName = entry.getKey();
            int count = entry.getValue();

            ItemPrice itemPrice = pricingService.getPrice(itemName);

            if (itemPrice == null) {
                // This case should ideally be caught during scanning, but good to check.
                continue;
            }

            // --- Core Calculation Logic (Ticket 5) ---

            if (itemPrice.offer() != null) {
                OfferRule offer = itemPrice.offer();

                // 1. Calculate how many times the offer can be applied (e.g., 5 apples / 2 = 2
                // offers)
                int offerBatches = count / offer.quantity();

                // Add the price of the offer batches to the total
                total += offerBatches * offer.price();

                // 2. Calculate the remaining items that must be charged at unit price (e.g., 5
                // apples % 2 = 1 remaining)
                int remainingItems = count % offer.quantity();

                // Add the price of the remaining items to the total
                total += remainingItems * itemPrice.unitPrice();
            } else {
                // No special offer, charge all items at unit price
                total += count * itemPrice.unitPrice();
            }
        }
        return total;
    }

    public int updateItems(List<ItemDto> items) {
        int total = 0;
        for (ItemDto item : items) {
            ItemPrice priceInfo = pricingService.getPrice(item.getName());
            if (priceInfo != null) {
                total += calculateItemTotal(priceInfo, item.getQuantity());
            }
        }
        return total;
    }

    private int calculateItemTotal(ItemPrice priceInfo, int quantity) {
        OfferRule offer = priceInfo.offer();
        if (offer != null && quantity >= offer.quantity()) {
            int offerSets = quantity / offer.quantity();
            int remainingItems = quantity % offer.quantity();
            return (offerSets * offer.price()) + (remainingItems *
                    priceInfo.unitPrice());
        }
        return quantity * priceInfo.unitPrice();
    }

    /**
     * Gets the current contents of the basket (item name and count).
     *
     * @return An immutable map of items and their quantities.
     */
    public Map<String, Integer> getBasketContents() {
        return Map.copyOf(basket);
    }
}
