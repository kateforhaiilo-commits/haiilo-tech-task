package com.supermarket.service;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;
import java.util.Collections;

/**
 * Data model for a special offer rule (e.g., 2 Apples for 45).
 *
 * @param quantity The number of items required for the offer.
 * @param price    The total price for the offer quantity.
 */
record OfferRule(int quantity, BigDecimal price) {
}

/**
 * Service to manage the static pricing data
 */
@Service
public class PricingService {

        private final Map<String, ItemPrice> pricingMap;

        public PricingService() {
                Map<String, ItemPrice> tempMap = new HashMap<>();

                tempMap.put("Aurora Apple",
                                new ItemPrice("Aurora Apple", new BigDecimal(3.00),
                                                new OfferRule(2, new BigDecimal(4.5)), 0));
                tempMap.put("Blazing Banana",
                                new ItemPrice("Blazing Banana", new BigDecimal(5.25),
                                                new OfferRule(3, new BigDecimal(13)), 0));
                tempMap.put("Shimmering Starfruit",
                                new ItemPrice("Shimmering Starfruit", new BigDecimal(6.66), null, 0));
                tempMap.put("Kismet Kiwi",
                                new ItemPrice("Kismet Kiwi", new BigDecimal(3.75),
                                                new OfferRule(2, new BigDecimal(6.5)), 0));
                tempMap.put("Prism Pear", new ItemPrice("Prism Pear", new BigDecimal(2.50), null,
                                0));
                tempMap.put("Celestial Carrot",
                                new ItemPrice("Celestial Carrot", new BigDecimal(1.99), new OfferRule(666,
                                                new BigDecimal(999)), 0));

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
