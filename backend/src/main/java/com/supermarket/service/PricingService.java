package com.supermarket.service;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * Data model for a special offer rule (e.g., 2 Apples for 45).
 *
 * @param quantity The number of items required for the offer.
 * @param price    The total price for the offer quantity.
 */
record OfferRule(int quantity, int price) {
}

/**
 * Data model for an individual item, including its unit price and special
 * offer.
 *
 * @param name      The unique name of the item (e.g., "Apple").
 * @param unitPrice The standard price for one item.
 * @param offer     The special offer rule, or null if no offer exists.
 */
record ItemPrice(String name, int unitPrice, OfferRule offer) {
}

/**
 * Service to manage the static pricing data
 */
@Service
public class PricingService {

    private final Map<String, ItemPrice> pricingMap;

    public PricingService() {
        Map<String, ItemPrice> tempMap = new HashMap<>();

        // Apple: 30 each, 2 for 45
        tempMap.put("Apple", new ItemPrice("Apple", 30, new OfferRule(2, 45)));

        // Banana: 50 each, 3 for 130
        tempMap.put("Banana", new ItemPrice("Banana", 50, new OfferRule(3, 130)));

        // Peach: 60 each, no offer
        tempMap.put("Peach", new ItemPrice("Peach", 60, null));

        // Kiwi: 20 each, no offer
        tempMap.put("Kiwi", new ItemPrice("Kiwi", 20, null));

        // Make the pricing table immutable for safety
        this.pricingMap = Collections.unmodifiableMap(tempMap);
    }

    /**
     * Retrieves the pricing details for a given item name.
     *
     * @param itemName The name of the item.
     * @return The ItemPrice object, or null if the item is not found.
     */
    public ItemPrice getPrice(String itemName) {
        return pricingMap.get(itemName);
    }

    /**
     * Returns the full map of current pricing rules.
     *
     * @return Immutable map of item names to their prices/offers.
     */
    public Map<String, ItemPrice> getAllPricing() {
        return pricingMap;
    }
}
